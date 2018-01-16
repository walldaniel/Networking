package com.wall.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Explosion;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;

public class PlayScreen implements Screen {

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;

	private Player player;
	private ArrayList<Laser> lasers;
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Explosion> explosions;

	public PlayScreen() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, AsteroidGame.WIDTH, AsteroidGame.HEIGHT);

		shapeRenderer = new ShapeRenderer();

		lasers = new ArrayList<Laser>();
		asteroids = new ArrayList<Asteroid>();
		explosions = new ArrayList<Explosion>();

		player = new Player(32, 32);
	}

	public void update(float dt) {
		// Get the user input
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.addRotationalForce(dt * 32f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.addRotationalForce(-dt * 32f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.addForceForward(dt * 16f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.addForceForward(-dt * 16f);
		}

		// If the space bar is pressed launch new bullet at the center of the sprite
		// TODO: fix the lasers from ship
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			Laser laser = new Laser(player.getX(), player.getY(), player.getDirectionInDegrees());
			lasers.add(laser);
		}

		// Update the location of the ship
		player.update();

		// Update all the lasers position
		// Also sees if the laser is out of bounds and removes
		for (int i = lasers.size() - 1; i >= 0; i--) {
			if (lasers.get(i).update(dt)) {
				lasers.remove(i);
				i--;
			}
		}

		// Update all the asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			// Also checks if the asteroid is out of bounds to delete it
			if (asteroids.get(i).update(dt)) {
				asteroids.remove(i);

				// If the asteroid is removed make sure that you noe check the current i by
				// decrementing it
				i--;
			}
		}

		// Update all the explosions
		for (Explosion e : explosions) {
			if (e.update()) {
				explosions.remove(e);
			}
		}

		// Check if laser has collided with an asteroid
		for (int i = 0; i < asteroids.size(); i++) {
			for (int j = 0; j < lasers.size(); j++) {
				if (Intersector.overlapConvexPolygons(asteroids.get(i).getShape(), lasers.get(j).getShape())) {
					// If collision occurs, delete the objects and break out of inner for loop
					// Also if asteroid is larger create one that is smaller
					if (asteroids.get(i).getSize() > 48) {
						asteroids.add(new Asteroid(asteroids.get(i).getX(), asteroids.get(i).getY(),
								(float) (Math.random() * 365),
								(int) (asteroids.get(i).getSize() * (Math.random() * 0.3f + 0.5f)),
								(int) (Math.random() * 3 + 4)));
					}

					// Add an explosion at the coordinates
					explosions.add(new Explosion(asteroids.get(i).getX(), asteroids.get(i).getY()));

					asteroids.remove(i);
					i--;
					lasers.remove(j);
					break;
				}
			}
		}

		// Random chance to spawn an asteroid, 1/20 chance
		if (Math.random() > 0.96f) {
			// Randomly choose which side to generate the asteroid on
			// TODO: Have the asteroid spawn with a direction towards the player
			switch ((int) (Math.random() * 4)) {
			case 0: // left
				asteroids.add(new Asteroid(-16, (float) Math.random() * AsteroidGame.HEIGHT,
						(float) (Math.random() * 90 + 45), 63, (int) (Math.random() * 3 + 4)));
				break;
			case 1: // right
				asteroids.add(new Asteroid(AsteroidGame.WIDTH + 16f, (float) Math.random() * AsteroidGame.HEIGHT,
						(float) (Math.random() * 90 + 225), 63, (int) (Math.random() * 3 + 4)));
				break;
			case 2: // top
				asteroids.add(new Asteroid((float) Math.random() * AsteroidGame.WIDTH, AsteroidGame.HEIGHT + 16f,
						(float) (Math.random() * 90 + 135), 63, (int) (Math.random() * 3 + 4)));
				break;
			case 3: // bottom
				asteroids.add(new Asteroid((float) Math.random() * AsteroidGame.WIDTH, -16f,
						(float) (Math.random() * 90 + 305), 63, (int) (Math.random() * 3 + 4)));
				break;
			}
		}
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
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 1, 1);

		// Player stuff
		shapeRenderer.polygon(player.getShip().getTransformedVertices());

		// Draw all the lasers
		for (Laser l : lasers) {
			shapeRenderer.polygon(l.getShape().getTransformedVertices());
		}

		// Draw the asteroids
		for (Asteroid a : asteroids) {
			shapeRenderer.polygon(a.getShape().getTransformedVertices());
		}

		// Draw the explosions
		for (Explosion e : explosions) {
			float angle = 0;

			while (angle < 360) {
				shapeRenderer.line(e.getX(), e.getY(), e.getX() + MathUtils.sinDeg(angle) * e.getSize(),
						e.getY() + MathUtils.cosDeg(angle) * e.getSize());
				angle += e.getAngle();
			}
		}

		shapeRenderer.end();
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
		shapeRenderer.dispose();
	}

}
