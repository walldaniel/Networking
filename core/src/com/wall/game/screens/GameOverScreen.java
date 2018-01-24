package com.wall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.wall.game.AsteroidGame;

public class GameOverScreen implements Screen {
	private AsteroidGame game;

	private BitmapFont font;
	private boolean isButtonPressed;
	private int score;

	public GameOverScreen(AsteroidGame game, int score) {
		this.game = game;
		// Gets the score from the game
		this.score = score;

		// Load the font to draw text
		font = new BitmapFont(Gdx.files.internal("assets/GameOverFont.fnt"));

		// Used to only restart the game once the user releases the button
		isButtonPressed = false;
	}

	public void update(float dt) {
		// Restarts the game once the player releases a mouse button
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT) 
				|| Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			isButtonPressed = true;
		} else {
			if (isButtonPressed) {
				game.setScreen(new PlayScreen(game));
			}
		}
	}

	@Override
	public void render(float delta) {
		update(delta);

		// Clear the background to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getSb().begin();

		// Draw the score that the user got
		font.draw(game.getSb(), "Score: " + score, 32, 332);
		// Draw the losing message
		font.draw(game.getSb(), "You Win!!!!...", 32, 288);
		font.draw(game.getSb(), "at being a loser oooo! haha", 32, 256);
		// Draw the instructions on what to do next
		font.draw(game.getSb(), "?!? Click the mouse to try again?", 32, 96);
		
		game.getSb().end();
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
		font.dispose();
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

}
