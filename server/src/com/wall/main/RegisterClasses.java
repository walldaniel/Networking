package com.wall.main;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Explosion;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;

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
	}
}
