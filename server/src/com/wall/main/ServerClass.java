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
import com.wall.game.AsteroidGame;
import com.wall.game.RegisterClasses;
import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;

public class ServerClass {

	private GameObjects objects;
	private Server server;

	public ServerClass() throws IOException {
		objects = new GameObjects();

		server = new Server();

		server.start();
		server.bind(54555, 54777);

		// Register
		RegisterClasses.register(server);

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof String) {
					server.sendToAllExceptTCP(connection.getID(), object);

					objects.addPlayer((Player) object);
				} else if (object instanceof Asteroid) {
					// Send the object to all connections
					server.sendToAllExceptTCP(connection.getID(), object);

					// Update the asteroid stuff
					objects.addAsteroid((Asteroid) object);
				} else if (object instanceof Laser) {
					// Send the object to all connections
					server.sendToAllExceptTCP(connection.getID(), object);

					// Update the asteroid stuff
					objects.addLaser((Laser) object);
				} else if (object instanceof String) {
					if (((String) object).equals("EVERYTHING")) {
						server.sendToTCP(connection.getID(), objects.getEverything());
					}
				}
			}
		});

		// Schedule to randomly spawn an asteroid
		Timer time = new Timer();
		SpawnAsteroid spawn = new SpawnAsteroid();
		time.schedule(spawn, 0, 1000);

		// Schedule task to update the positions for the clients
		Updating update = new Updating();
		time.schedule(update, 0, 100);
	}

	private class Updating extends TimerTask {
		private long lastTime;

		@Override
		public void run() {
			Update.update((lastTime - (lastTime = System.currentTimeMillis())) * -1, objects);
			server.sendToAllTCP(Update.updateObjects(objects));
		}

	}

	private class SpawnAsteroid extends TimerTask {

		@Override
		public void run() {
			// Check if anyone is connected
			if (server.getConnections().length < 1)
				return;

			// Randomly choose which side to generate the asteroid on
			// TODO: Have the asteroid spawn with a direction towards the player
			Asteroid a;
			switch ((int) (Math.random() * 4)) {
			case 0: // left
				a = new Asteroid(-16, (float) Math.random() * AsteroidGame.HEIGHT, (float) (Math.random() * 90 + 45),
						(int) (Math.random() * 32 + 48), (int) (Math.random() * 3 + 4));
				break;
			case 1: // right
				a = new Asteroid(AsteroidGame.WIDTH + 16f, (float) Math.random() * AsteroidGame.HEIGHT,
						(float) (Math.random() * 90 + 225), (int) (Math.random() * 32 + 48),
						(int) (Math.random() * 3 + 4));
				break;
			case 2: // top
				a = new Asteroid((float) Math.random() * AsteroidGame.WIDTH, AsteroidGame.HEIGHT + 16f,
						(float) (Math.random() * 90 + 135), (int) (Math.random() * 32 + 48),
						(int) (Math.random() * 3 + 4));
				break;
			case 3: // bottom
				a = new Asteroid((float) Math.random() * AsteroidGame.WIDTH, -16f, (float) (Math.random() * 90 + 305),
						(int) (Math.random() * 32 + 48), (int) (Math.random() * 3 + 4));
				break;
			default: // If something weird happens
				a = null;
			}

			// Send the asteroid to the clients and add to list
			server.sendToAllTCP(a);
			objects.addAsteroid(a);
		}

	}

	// Sends an object to all the players
	public void sendObject(Object object) {
		server.sendToAllTCP(object);
	}

	// Returns the ip address of the server computer: eg.: 168.192...
	// Got from: https://groups.google.com/forum/#!topic/kryonet-users/lxicKGTFcu4,
	// username: dannyG82
	public String getIp() throws Exception {
		for (NetworkInterface iFace : Collections.list(NetworkInterface.getNetworkInterfaces())) {
			for (InetAddress address : Collections.list(iFace.getInetAddresses())) {
				if (!address.isLoopbackAddress() && !address.isLinkLocalAddress())
					return address.getHostAddress().trim();
			}
		}

		return "unknown";
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		// Add the ip address
		try {
			sb.append(getIp());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return sb.toString();
	}

}
