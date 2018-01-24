package com.wall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.wall.game.AsteroidGame;

public class MenuScreen implements Screen {

	private AsteroidGame game;
	final private BitmapFont font;
	
	final String titleText;
	final GlyphLayout titleLayout;
	final float titleX, titleY;
	
	final String instructionsText;
	final GlyphLayout instructionsLayout;
	final float insX, insY;
	
	public MenuScreen(AsteroidGame game) {
		this.game = game;

		font = new BitmapFont(Gdx.files.internal("assets/MainMenuFont.fnt"));
		
		titleText = "ASTEROID GAME";
		instructionsText = "Press  enter  to  start";
		
		// Create the instructions with normal font size
		instructionsLayout = new GlyphLayout(font, instructionsText);

		// Calculate the position of the instructions
		insX = AsteroidGame.WIDTH / 2 - instructionsLayout.width / 2;
		insY = AsteroidGame.HEIGHT / 4 - instructionsLayout.height / 2;
		
		// Set the size of the font to larger for the title
		font.getData().setScale(2);
		titleLayout = new GlyphLayout(font, titleText);
		
		// Calculate the position of the title, more efficient to just store in variable
		titleX = AsteroidGame.WIDTH / 2 - titleLayout.width / 2;
		titleY = AsteroidGame.HEIGHT / 1.4f - titleLayout.height / 2;
		
	}
	
	public void update(float dt) {
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.ENTER) 
				|| Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			game.setScreen(new PlayScreen(game));
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		
		game.getSb().begin();
		
		font.getData().setScale(2);
		font.draw(game.getSb(), titleLayout, titleX, titleY);
		font.getData().setScale(1);
		font.draw(game.getSb(), instructionsLayout, insX, insY);
		
		game.getSb().end();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
