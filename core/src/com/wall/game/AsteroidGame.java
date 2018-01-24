package com.wall.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wall.game.screens.MenuScreen;

public class AsteroidGame extends Game {
	// The width and height of the screen in pixels
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	public SpriteBatch sb;

	public SpriteBatch getSb() {
		return sb;
	}

	@Override
	public void create() {
		sb = new SpriteBatch();

		// Initialize and set the screen to the playing screen
		this.setScreen(new MenuScreen(this));
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
