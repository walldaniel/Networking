package com.wall.game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.wall.game.Laser.LaserStat;

public class RegisterClassesForServer {

	public static void registerServer(Server server) {
		Kryo kryo = server.getKryo();
		registerClasses(kryo);
	}
	
	public static void registerClient(Client client) {
		Kryo kryo = client.getKryo();
		registerClasses(kryo);
	}
	
	private static void registerClasses(Kryo kryo) {
		kryo.register(String.class);
		kryo.register(Long.class);
		kryo.register(Player.PlayerStats.class);
		kryo.register(Player.class);
		kryo.register(Integer.class);
		kryo.register(Laser.class);
		kryo.register(LaserStat.class);
	}
}
