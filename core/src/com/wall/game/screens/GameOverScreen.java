package com.wall.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen implements Screen {
	private Game game;
	private SpriteBatch sb;

	private BitmapFont font;
	private boolean isButtonPressed;

	public GameOverScreen(Game game, SpriteBatch sb) {
		this.sb = sb;
		this.game = game;

		font = new BitmapFont(Gdx.files.internal("assets/font.fnt"));

		// Used to only restart the game once the user releases the button
		isButtonPressed = false;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	public void update(float dt) {
		// Restarts the game once the player releases a mouse button
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT) 
				|| Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			isButtonPressed = true;
		} else {
			if (isButtonPressed) {
				game.setScreen(new PlayScreen(game, sb));
			}
		}
	}

	@Override
	public void render(float delta) {
		update(delta);

		// Clear the background to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sb.begin();
		
		font.draw(sb, "You Win!!!!...", 32, 288);
		font.draw(sb, "at being a loser oooo! haha", 32, 256);
		font.draw(sb, "?!? Click the mouse to try again?", 32, 96);
		
		sb.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
