package com.wall.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends com.badlogic.gdx.Game {

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	SpriteBatch sb;

	@Override
	public void create() {
		sb = new SpriteBatch();

		this.setScreen(new PlayScreen(this));
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
