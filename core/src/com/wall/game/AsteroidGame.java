package com.wall.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;
import com.wall.game.objects.Player.PlayerStats;

public class AsteroidGame extends Game {

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
			// First try and automatically find a the server on the network,
			// then ask user for ip address
			InetAddress address = client.discoverHost(54777, 5000);
			if (address != null) {
				client.connect(2000, address, 54555, 54777);
			} else {
				br = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Enter the ip of the server (192.168..): ");
				String input = br.readLine();
				// If input is no then don't connect to a server
				if (input.equals("no")) {
					// Every 1000ms create a new enemy if no server
					Timer time = new Timer();
					time.schedule(new TimerTask() {

						@Override
						public void run() {

							// Randomly choose which side to generate the asteroid on
							// TODO: Have the asteroid spawn with a direction towards the player
							switch ((int) (Math.random() * 4)) {
							case 0: // left
								screen.addEnemy(new Asteroid(-16, (float) Math.random() * AsteroidGame.HEIGHT,
										(float) (Math.random() * 90 + 45), 63, (int) (Math.random() * 3 + 4)));
								break;
							case 1: // right
								screen.addEnemy(new Asteroid(AsteroidGame.WIDTH + 16f,
										(float) Math.random() * AsteroidGame.HEIGHT, (float) (Math.random() * 90 + 225),
										63, (int) (Math.random() * 3 + 4)));
								break;
							case 2: // top
								screen.addEnemy(new Asteroid((float) Math.random() * AsteroidGame.WIDTH,
										AsteroidGame.HEIGHT + 16f, (float) (Math.random() * 90 + 135), 63,
										(int) (Math.random() * 3 + 4)));
								break;
							case 3: // bottom
								screen.addEnemy(new Asteroid((float) Math.random() * AsteroidGame.WIDTH, -16f,
										(float) (Math.random() * 90 + 305), 63, (int) (Math.random() * 3 + 4)));
								break;
							}
						}
					}, 500, 1000);
				} else { // Try and connect to the server user inputted
					client.connect(5000, br.readLine(), 54555, 54777);

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
							if (object instanceof Laser) {
								screen.addLaser((Laser) object);
							}
							if (object instanceof Asteroid) {
								if (screen != null)
									screen.addEnemy((Asteroid) object);
							}
							if (object instanceof Player) {
								// Add player to list of players
								if (screen != null)
									screen.addPlayer((Player) object);
							}
							if (object instanceof Long) {
								System.out.println(System.currentTimeMillis() - (Long) object);

								connection.sendTCP(System.currentTimeMillis());
							}
						}
					});
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

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
