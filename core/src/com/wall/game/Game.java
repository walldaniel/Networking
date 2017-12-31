package com.wall.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.wall.game.Player.PlayerStats;

public class Game extends com.badlogic.gdx.Game {

	private BufferedReader br;

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	SpriteBatch sb;

	public Client client;
	public PlayScreen screen;

	@Override
	public void create() {
		client = new Client();
		client.start();
		try {
			// First try and automaticaly find a the server on the network, then ask user
			// for ip address
			InetAddress address = client.discoverHost(54777, 5000);
			if (address != null) {
				client.connect(5000, address, 54555, 54777);
			} else {
				br = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Enter the ip of the server (192.168..): ");
				client.connect(5000, br.readLine(), 54555, 54777);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		RegisterClassesForServer.registerClient(client);

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof String) {
					String s = (String) object;
					if (s.equals("GET_PLAYERS")) {
						client.sendTCP(screen.getPlayers().get(screen.myPlayerindex));
					} else {
						PlayerStats ps = new PlayerStats(s);

						screen.updatePlayerPos(ps);
					}
				}
				if (object instanceof Player) {
					// Add player to list of players
//					Player player = (Player) object;
					screen.addPlayer((Player) object);
				}
				if (object instanceof Long) {
					System.out.println(System.currentTimeMillis() - (Long) object);

					connection.sendTCP(System.currentTimeMillis());
				}
			}
		});
//		screen.myPlayerindex = client.getID();

		sb = new SpriteBatch();

		screen = new PlayScreen(this);
		this.setScreen(screen);

	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		sb.dispose();
	}
}
