package com.wall.game.screens;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.wall.game.AsteroidGame;
import com.wall.game.objects.Asteroid;

public class MenuScreen implements Screen {

	private AsteroidGame game;
	final private BitmapFont font;

	final String titleText;
	final GlyphLayout titleLayout;
	final float titleX, titleY;

	final String instructionsText;
	final GlyphLayout instructionsLayout;
	final float insX, insY;

	private ArrayList<Asteroid> asteroids;

	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	public MenuScreen(AsteroidGame game) {
		this.game = game;

		// Stuff used to display asteroids
		cam = new OrthographicCamera();
		cam.setToOrtho(false, AsteroidGame.WIDTH, AsteroidGame.HEIGHT);
		shapeRenderer = new ShapeRenderer();

		font = new BitmapFont(Gdx.files.internal("assets/MainMenuFont.fnt"));

		// Text to display on the main screen
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

		// Add an asteroid that goes across the screen every 1.5 seconds
		asteroids = new ArrayList<Asteroid>();

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// Check if the renderer is drawing
				if (!shapeRenderer.isDrawing())
					asteroids.add(
							new Asteroid(-32f, (float) (Math.random() * 175 + 100f), (float) (Math.random() * 80 + 50),
									(float) (Math.random() * 32 + 40), (int) Math.random() * 3 + 4));
			}
		}, 600);
	}

	public void update(float dt) {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.ENTER)
				|| Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			game.setScreen(new PlayScreen(game));
		}

		// Check if the asteroids have gone out of the screen and update the asteroid's positions
		for (int i = 0; i < asteroids.size(); i++) {
			if (asteroids.get(i).update(dt)) {
				asteroids.remove(i);
				i--;
			}
		}
	}

	@Override
	public void render(float delta) {
		// Clear the background to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(delta);
		cam.update();

		// Draw the asteroid
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 1, 1);

		for (Asteroid a : asteroids) {
			shapeRenderer.polygon(a.getShape().getTransformedVertices());
		}

		shapeRenderer.end();

		// Draw the texts
		game.getSb().setProjectionMatrix(cam.combined);
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
		font.dispose();
	}

}
