package com.wall.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AsteroidGame extends Game {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	public PlayScreen screen;
	public SpriteBatch sb;

	@Override
	public void create() {
		sb = new SpriteBatch();
		
		// Initialize and set the screen to the playing screen
		screen = new PlayScreen(sb);
		this.setScreen(screen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		screen.dispose();
	}
}
