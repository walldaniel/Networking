package com.wall.game;

import com.badlogic.gdx.math.Polygon;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;

public class RegisterClassesForServer {

	public static void registerServer(Server server) {
		registerClasses(server.getKryo());
	}
	
	public static void registerClient(Client client) {
		registerClasses(client.getKryo());
	}
	
	private static void registerClasses(Kryo kryo) {
		kryo.register(String.class);
		kryo.register(Long.class);
		kryo.register(Player.PlayerStats.class);
		kryo.register(Player.class);
		kryo.register(Integer.class);
		kryo.register(Laser.class);
		kryo.register(Asteroid.class);
		kryo.register(Polygon.class);
		kryo.register(float[].class);
		kryo.register(com.badlogic.gdx.math.Vector2.class);
	}
}
