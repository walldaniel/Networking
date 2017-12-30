package com.wall.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

public class PlayScreen implements Screen {

	final Game game;
	private OrthographicCamera camera;

	private Texture shipTex;
	private Sprite shipSprite;

	private Texture laserTex;
	private Sprite laserSprite;

	private ArrayList<Player> players;
	private ArrayList<Laser> lasers;

	private int myPlayerindex;

	public PlayScreen(final Game game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		shipTex = new Texture("ship.png");
		shipSprite = new Sprite(shipTex);
		laserTex = new Texture("laser.png");
		laserSprite = new Sprite(laserTex);

		players = new ArrayList<Player>();
		lasers = new ArrayList<Laser>();

		myPlayerindex = players.size();
		players.add(new Player(32, 32, Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S, Input.Keys.SPACE));
	}

	public void update(float dt) {
		// Check if a laser has exited the screen
		// TODO: make removal of lasers more efficient
		for (int i = lasers.size() - 1; i >= 0; i--) {
			if (!(lasers.get(i).getX() > -16f && lasers.get(i).getX() < Laser.REMOVAL_X)
					|| !(lasers.get(i).getY() > -16f && lasers.get(i).getY() < Laser.REMOVAL_Y))
				lasers.remove(i);
		}

		// Update all the lasers position
		for (int i = lasers.size() - 1; i >= 0; i--) {
			lasers.get(i).update(dt);
		}

		// Send the data to the server
		game.client.sendTCP(new Player.PlayerStats(players.get(myPlayerindex).getX(), players.get(myPlayerindex).getY(),
				players.get(myPlayerindex).getDirectionInRads()).sendTcp());

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float dt) {
		// Get updates from user
		update(Gdx.graphics.getDeltaTime());

		// Clear the background to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.sb.setProjectionMatrix(camera.combined);

		game.sb.begin();

		// Player stuff
		for (Player player : players) {
			if (Gdx.input.isKeyPressed(player.turnLeft)) {
				player.addDirection(dt * 8f);
			}
			if (Gdx.input.isKeyPressed(player.turnRight)) {
				player.addDirection(-dt * 8f);
			}
			if (Gdx.input.isKeyPressed(player.forward)) {
				player.addX(-8f * dt * (float) Math.toDegrees(Math.sin(player.getDirectionInRads())));
				player.addY(8f * dt * (float) Math.toDegrees(Math.cos(player.getDirectionInRads())));
			}
			if (Gdx.input.isKeyPressed(player.backward)) {
				player.addX(8f * dt * (float) Math.toDegrees(Math.sin(player.getDirectionInRads())));
				player.addY(-8f * dt * (float) Math.toDegrees(Math.cos(player.getDirectionInRads())));
			}

			shipSprite.setRotation((float) Math.toDegrees(player.getDirectionInRads()));
			shipSprite.setX(player.getX());
			shipSprite.setY(player.getY());
			shipSprite.draw(game.sb);

			// If the space bar is pressed launch new bullet at the center of the sprite
			if (Gdx.input.isKeyJustPressed(player.shoot)) {
				lasers.add(new Laser(
						(shipSprite.getVertices()[SpriteBatch.X2] + shipSprite.getVertices()[SpriteBatch.X3]) / 2f,
						(shipSprite.getVertices()[SpriteBatch.Y2] + shipSprite.getVertices()[SpriteBatch.Y3]) / 2f,
						player.getDirectionInRads()));
			}
		}

		// Draw all the lasers
		for (Laser laser : lasers) {
			laserSprite.setRotation((float) Math.toDegrees(laser.getDirectionInRads()));
			laserSprite.setX(laser.getX());
			laserSprite.setY(laser.getY());
			laserSprite.draw(game.sb);
		}

		game.sb.end();
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
		shipTex.dispose();
	}

}
