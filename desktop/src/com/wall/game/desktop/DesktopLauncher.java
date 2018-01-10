package com.wall.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wall.game.Game;

public class DesktopLauncher {

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.x = Game.WIDTH;
		config.y = Game.HEIGHT;
		config.foregroundFPS = 60;
		
		new LwjglApplication(new Game(), config);
	}
}
