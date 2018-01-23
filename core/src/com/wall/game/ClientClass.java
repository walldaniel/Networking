package com.wall.game;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Explosion;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;
import com.wall.game.objects.UpdatePosition;
import com.wall.game.screens.PlayScreen;

public class ClientClass {

	private Client client;

	public ClientClass(final PlayScreen game) {

		// Initialize the client and connect to server
		client = new Client();
		client.start();

		try {
			// Try and automatically find the server
			InetAddress address = client.discoverHost(54777, 5000);

			if (address != null) {
				client.connect(1000, address, 54555, 54777);
			} else {
				// TODO: connect to a specific address
			}

			// Register client so that it can serialize all objects
			RegisterClasses.register(client);

			// Add the listener to tell client what to do when it receives stuff
			client.addListener(new Listener() {
				public void received(Connection connection, Object object) {
					if (object instanceof ArrayList) {
						ArrayList<Object> arr = (ArrayList) object;

						// Update the asteroid positions
						for (Object o : arr) {
							if (o instanceof UpdatePosition) {
								try {
									game.updatePosition((UpdatePosition) o);
								} catch(IndexOutOfBoundsException e) {
									System.out.println("Oh nooes!");
								}
							} else if(o instanceof Asteroid) {
								game.addAsteroid((Asteroid) o);
							} else if(o instanceof Laser) {
								game.addLaser((Laser) o);
							}
						}
					} else if (object instanceof Player) {
						game.addPlayer((Player) object);
					} else if (object instanceof Asteroid) {
						game.addAsteroid((Asteroid) object);
					} else if (object instanceof Laser) {
						game.addLaser((Laser) object);
					} else if (object instanceof Explosion) {
						game.addExplosion((Explosion) object);
					}
				}
			});

			// Ask the server for everything
			client.sendTCP("EVERYTHING");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return client.getID();
	}
	
	public boolean isConnected() {
		return client.isConnected();
	}

}
