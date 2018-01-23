package com.wall.main;

import java.io.IOException;

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
