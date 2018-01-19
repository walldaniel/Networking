package com.wall.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;

public class ServerClass {

	private ArrayList<Player> players;
	private ArrayList<Laser> lasers;
	private ArrayList<Asteroid> asteroids;
	
	Server server;

	public ServerClass() throws IOException {
		// Create the list of objects
		players = new ArrayList<Player>();
		lasers = new ArrayList<Laser>();
		asteroids = new ArrayList<Asteroid>();
		
		server = new Server();

		server.start();
		server.bind(54555, 54777);
		
		// Register 
		RegisterClasses.register(server);
		
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if(object instanceof String) {
					
				} else if(object instanceof Asteroid) {
					// Send the object to all connections
					server.sendToAllExceptTCP(connection.getID(), object);
					
					// Update the asteroid stuff
					asteroids.add((Asteroid) object);
				} else if(object instanceof Laser) {
					// Send the object to all connections
					server.sendToAllExceptTCP(connection.getID(), object);
					
					// Update the asteroid stuff
					lasers.add((Laser) object);
				} 
			}
		});
	}

	// Sends an object to all the players
	public void sendObject(Object object) {
		server.sendToAllTCP(object);
	}
	
	// Returns the ip address of the server computer: eg.: 168.192...
	// Got from: https://groups.google.com/forum/#!topic/kryonet-users/lxicKGTFcu4,
	// username: dannyG82
	public static String getIp() throws Exception {
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
