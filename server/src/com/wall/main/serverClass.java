package com.wall.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.wall.game.Game;
import com.wall.game.RegisterClassesForServer;
import com.wall.game.objects.Enemy;
import com.wall.game.objects.Player;
import com.wall.game.objects.Vector2;
import com.wall.game.objects.Laser.LaserStat;

/*
 * Used to create a server to control the conections between players
 */
public class serverClass {

	// Got from: https://groups.google.com/forum/#!topic/kryonet-users/lxicKGTFcu4, username: dannyG82
	public static String getIp() throws Exception {
		for(NetworkInterface iFace : Collections.list(NetworkInterface.getNetworkInterfaces())) {
			for(InetAddress address : Collections.list(iFace.getInetAddresses())) {
				if(!address.isLoopbackAddress() && !address.isLinkLocalAddress())
					return address.getHostAddress().trim();
			}
		}
		
		return "unknown";
	}
	
	public static void main(String[] args) throws IOException {
		final Server server = new Server();
		server.start();
		server.bind(54555, 54777);

		RegisterClassesForServer.registerServer(server);

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof String) {
					String s = (String) object;
					if (s.equals("GET_PLAYERS")) {
						server.sendToAllTCP("GET_PLAYERS");
					} else {
						// PlayerStats ps = new PlayerStats((String) object);
						// System.out.println(ps.x + " - " + ps.y);
						System.out.println(connection.getID());
						server.sendToAllExceptTCP(connection.getID(), object);
					}
				}
				if (object instanceof LaserStat) {
					server.sendToAllExceptTCP(connection.getID(), object);
				}
				if (object instanceof Player) {
					// This is called whenever a new player joins the game
					// Send the player object to every connection
					server.sendToAllTCP(object);
				}
			}
		});

		// Every 1000ms send a new enemy to the clients
		Timer time = new Timer();
		time.schedule(new TimerTask() {

			@Override
			public void run() {
				float[] x;
				float[] y;
				switch((int) Math.random() * 3) {
				case 0:
					x = new float[4];
					y = new float[4];
					break;
				case 1:
					x = new float[5];
					y = new float[5];
					break;
				case 2:
					x = new float[6];
					y = new float[6];
					break;
					default:
						break;
				}
				
				if(x != null)
					server.sendToAllTCP(new Enemy((float) (Math.random() * 5f + 25), (float) (Math.random() * 5f + 25), Math.random() * 360, x, y));

			}
		}, 250, 1000);

	}

}
