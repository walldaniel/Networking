package com.wall.game.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.wall.game.Game;
import com.wall.game.Player;
import com.wall.game.RegisterClassesForServer;
import com.wall.game.Player.PlayerStats;

public class DesktopLauncher {
	public static void main(String[] arg) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Is this the server (y/n)? : ");
		if (br.readLine().charAt(0) == 'y') {
			Server server = new Server();
			server.start();
			server.bind(54555, 54777);

			RegisterClassesForServer.registerServer(server);

			server.addListener(new Listener() {
				public void received(Connection connection, Object object) {
					if (object instanceof String) {
						PlayerStats ps = new PlayerStats((String) object);
						System.out.println(ps.x + " - " + ps.y);
						connection.sendTCP("received");
					}
					if(object instanceof Long) {
						System.out.println(System.currentTimeMillis() - (Long) object);
						
						connection.sendTCP(System.currentTimeMillis());
					}
				}
			});
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x = Game.WIDTH;
		config.y = Game.HEIGHT;
		config.foregroundFPS = 60;
		new LwjglApplication(new Game(), config);

	}
}
