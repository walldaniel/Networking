package com.wall.game.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.wall.game.Enemy;
import com.wall.game.Game;
import com.wall.game.Laser.LaserStat;
import com.wall.game.Player;
import com.wall.game.RegisterClassesForServer;

public class DesktopLauncher {

	public static void main(String[] arg) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Is this the server (y/n)? : ");
		if (br.readLine().charAt(0) == 'y') {
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

			Timer time = new Timer();

			time.schedule(new TimerTask() {

				@Override
				public void run() {
					server.sendToAllTCP(new Enemy((float) Math.random() * Game.WIDTH, (float) Math.random() * Game.HEIGHT));
				}
			}, 0, 1000);
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x = Game.WIDTH;
		config.y = Game.HEIGHT;
		config.foregroundFPS = 60;
		new LwjglApplication(new Game(), config);

	}
}
