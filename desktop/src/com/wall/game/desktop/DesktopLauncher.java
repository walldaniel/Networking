package com.wall.game.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.wall.game.Game;

public class DesktopLauncher {
	public static void main(String[] arg) throws IOException {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x = Game.WIDTH;
		config.y = Game.HEIGHT;
		config.foregroundFPS = 60;
		new LwjglApplication(new Game(), config);

		ArrayList<String> addresses = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface ni : Collections.list(interfaces)) {
				for (InetAddress address : Collections.list(ni.getInetAddresses())) {
					if (address instanceof Inet4Address)
						addresses.add(address.getHostAddress());
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();
		for (String str : addresses) {
			sb.append(str + "\n");
		}
		System.out.println(sb);

		new Thread(new Runnable() {

			@Override
			public void run() {
				ServerSocketHints serverSocketHint = new ServerSocketHints();
				serverSocketHint.acceptTimeout = 0;

				// Create the socket server using TCP protocol and listening on 9021
				// Only one app can listen to a port at a time, keep in mind many ports are
				// reserved
				// especially in the lower numbers ( like 21, 80, etc )
				ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 9021, serverSocketHint);

				while (true) {
					Socket socket = serverSocket.accept(null);

					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					try {
						System.out.println(br.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter ip address of other game: ");
		String ip = br.readLine();
		System.out.print("Enter message: ");
		String message = br.readLine();
		
		SocketHints socketHints = new SocketHints();
		socketHints.connectTimeout = 2000;
		Socket socket = Gdx.net.newClientSocket(Protocol.TCP, ip, 9021, socketHints);
		try {
			socket.getOutputStream().write(message.getBytes());
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
