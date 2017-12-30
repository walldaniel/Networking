package com.wall.game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

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
	}
}
