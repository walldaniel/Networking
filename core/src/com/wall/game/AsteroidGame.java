package com.wall.game;


import com.badlogic.gdx.Game;

public class AsteroidGame extends Game {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	public PlayScreen screen;

	@Override
	public void create() {
		// Initialize and set the screen to the playing screen
		screen = new PlayScreen();
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
