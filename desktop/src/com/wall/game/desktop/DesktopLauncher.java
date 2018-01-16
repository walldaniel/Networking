package com.wall.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wall.game.Game;

public class DesktopLauncher {

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = Game.WIDTH;
		config.height = Game.HEIGHT;
		config.foregroundFPS = 60;
		config.title = "Asteroids";
		
		new LwjglApplication(new Game(), config);
	}
}
