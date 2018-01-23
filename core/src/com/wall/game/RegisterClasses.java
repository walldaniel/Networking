package com.wall.game;

import java.util.ArrayList;

import com.badlogic.gdx.math.Polygon;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Explosion;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;
import com.wall.game.objects.UpdatePosition;

public class RegisterClasses {
	public static void register(Server server) {
		registerClasses(server.getKryo());
	}

	public static void register(Client client) {
		registerClasses(client.getKryo());
	}

	private static void registerClasses(Kryo kryo) {
		kryo.register(String.class);
		kryo.register(Asteroid.class);
		kryo.register(Explosion.class);
		kryo.register(Laser.class);
		kryo.register(Player.class);
		kryo.register(Polygon.class);
		kryo.register(float[].class);
		kryo.register(UpdatePosition.class);
		kryo.register(ArrayList.class);
	}
}
