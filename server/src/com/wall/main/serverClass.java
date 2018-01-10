package com.wall.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.wall.game.Enemy;
import com.wall.game.Game;
import com.wall.game.Player;
import com.wall.game.RegisterClassesForServer;
import com.wall.game.Laser.LaserStat;

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
					// Send player what player number they are in the array
					// connection.sendTCP(connection.getID()); <-- No longer needed because of
					// client.getid

					// Player player = (Player) object;
					// player.setPlayerNumber((short) connection.getID());
					server.sendToAllTCP((Player) object);
				}
			}
		});

		// Every 1000ms send a new enemy to the clients
		Timer time = new Timer();
		time.schedule(new TimerTask() {

			@Override
			public void run() {
				switch ((int) (Math.random() * 4)) {
				case 0: // Left side
					server.sendToAllTCP(new Enemy((float) Math.random() * 16, (float) Math.random() * Game.HEIGHT,
							Math.random() + 0.27));
					break;
				case 1: // Right side
					server.sendToAllTCP(new Enemy((float) Math.random() * 16 + Game.WIDTH,
							(float) Math.random() * Game.HEIGHT, Math.random() + 3.4));
					break;
				case 2: // Top
					server.sendToAllTCP(new Enemy((float) Math.random() * Game.WIDTH, (float) Math.random() * 16,
							Math.random() + 1.85));
					break;
				case 3: // Bottom
					server.sendToAllTCP(new Enemy((float) Math.random() * Game.WIDTH,
							(float) Math.random() * 16 + Game.HEIGHT, Math.random() - 0.27));
					break;
				default: // If this somehow happens then don't spawn anything
					break;
				}

			}
		}, 250, 1000);

	}

}
