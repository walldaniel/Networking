package com.wall.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import com.esotericsoftware.kryonet.Server;
import com.wall.game.AsteroidGame;
import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;

/*
 * Used to create a server to control the conections between players
 */
public class Networking {

	
	
	
	public static void main(String[] args) throws IOException {
		ServerClass server = new ServerClass();
		
		// Print out the ip of the server
		try {
			System.out.println("Server ip is: " + server.getIp());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
