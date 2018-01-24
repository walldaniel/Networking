package com.wall.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wall.game.AsteroidGame;

public class DesktopLauncher {

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		// Set the specifications of the game screen
		config.width = AsteroidGame.WIDTH;
		config.height = AsteroidGame.HEIGHT;
		config.foregroundFPS = 60;
		config.title = "Asteroids";
		
		new LwjglApplication(new AsteroidGame(), config);
	}
}
