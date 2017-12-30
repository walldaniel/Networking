package com.wall.game;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Game extends com.badlogic.gdx.Game {

	private BufferedReader br;
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	SpriteBatch sb;

	public Client client;
	
	@Override
	public void create() {
		sb = new SpriteBatch();

		this.setScreen(new PlayScreen(this));
		
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the ip of the server (192.168..): ");
		client = new Client();
		client.start();
		try {
			client.connect(5000, br.readLine(), 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RegisterClassesForServer.registerClient(client);
		
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if(object instanceof String) {
					System.out.println((String) object);
				}
				if(object instanceof Long) {
					System.out.println(System.currentTimeMillis() - (Long) object);
					
					connection.sendTCP(System.currentTimeMillis());
				}
			}
		});
		
		client.sendTCP(System.currentTimeMillis());
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
