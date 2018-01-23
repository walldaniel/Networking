package com.wall.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.wall.game.AsteroidGame;
import com.wall.game.ClientClass;
import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Explosion;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;
import com.wall.game.objects.UpdatePosition;

public class PlayScreen implements Screen {
	private Game game;
	private SpriteBatch sb;

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;

	private ClientClass client;

	private Player player;
	private ArrayList<Laser> lasers;
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Explosion> explosions;

	private BitmapFont font;
	private int lives; // How many lives the player has left before losing
	private int score;

	public PlayScreen(Game game, SpriteBatch sb) {
		this.sb = sb;
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, AsteroidGame.WIDTH, AsteroidGame.HEIGHT);

		shapeRenderer = new ShapeRenderer();

		lasers = new ArrayList<Laser>();
		asteroids = new ArrayList<Asteroid>();
		explosions = new ArrayList<Explosion>();

		player = new Player(32, 32);

		font = new BitmapFont();
		lives = 3; // Start with 3 lives, could change later
		score = 0;

		// Connect to the server
		client = new ClientClass(this);
	}

	public void update(float dt) {
		// Get the user input
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.addRotationalForce(dt * 40f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.addRotationalForce(-dt * 40f);
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
			lasers.add(new Laser(player.getShip().getTransformedVertices()[2],
					player.getShip().getTransformedVertices()[3], player.getDirectionInDegrees()));
		}

		// Update the location of the ship
		player.update(dt * 60);

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

		// Check if laser has collided with an asteroid
		for (int i = 0; i < asteroids.size(); i++) {
			for (int j = 0; j < lasers.size(); j++) {
				if (Intersector.overlapConvexPolygons(asteroids.get(i).getShape(), lasers.get(j).getShape())) {
					// Increase the score
					score += asteroids.get(i).getSize() * 2;

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

		// Check if an asteroid has hit the player and lose a life
		for (int i = 0; i < asteroids.size(); i++) {
			if (Intersector.overlapConvexPolygons(asteroids.get(i).getShape(), player.getShip())) {
				// Show an explosion at the player and asteroid position
				float[] vertices = player.getShip().getTransformedVertices();
				player.getShip().getOriginX();
				explosions.add(new Explosion((vertices[0] + vertices[4]) / 2, (vertices[1] + vertices[3]) / 2));
				explosions.add(new Explosion(asteroids.get(i).getX(), asteroids.get(i).getY()));

				// Remove the asteroid
				asteroids.remove(i);
				i--;

				// Decrease lives by one
				lives--;

				// If lives is 0 or less then game over screen
				// TODO: make game over screen
				if (lives < 1) {
					System.out.println("GAME OVER!");

					game.setScreen(new GameOverScreen(game, sb));
				}
			}
		}

		// Check if an asteroid has hit itself and change the direction
		// TODO: Actually get this to work
		// for (int i = 0; i < asteroids.size(); i++) {
		// for (int j = 0; j < asteroids.size(); j++) {
		// // Get how much it has to move
		// MinimumTranslationVector mtv = new MinimumTranslationVector();
		// Intersector.overlapConvexPolygons(asteroids.get(i).getShape().getTransformedVertices(),
		// asteroids.get(j).getShape().getTransformedVertices(), mtv);
		//
		// // If movement is not 0, then move both objects half way out
		// if(mtv.normal.x != 0 && mtv.normal.y != 0) {
		// asteroids.get(i).translate(mtv.normal.x / 2f, mtv.normal.y / 2f);
		// asteroids.get(j).translate(mtv.normal.x / 2f, mtv.normal.y / 2f);
		//
		// // Also reverse the angles for the asteroids
		// // TODO: do this better
		// float angle = asteroids.get(i).getDirectionInDegrees();
		// asteroids.get(i).setDirection(asteroids.get(j).getDirectionInDegrees());
		// asteroids.get(j).setDirection(angle);
		// }
		// }
		// }

		// Update all the explosions
		for (int i = 0; i < explosions.size(); i++) {
			if (explosions.get(i).update(dt * 60)) {
				explosions.remove(i);
			}
		}

		// If there is no server connection spawn asteroids
		if (client == null) {
			// Random chance to spawn an asteroid, 2/100 chance per frame
			if (Math.random() > 0.98f) {
				// Randomly choose which side to generate the asteroid on
				// TODO: Have the asteroid spawn with a direction towards the player
				switch ((int) (Math.random() * 4)) {
				case 0: // left
					asteroids.add(new Asteroid(-16, (float) Math.random() * AsteroidGame.HEIGHT,
							(float) (Math.random() * 90 + 45), (int) (Math.random() * 32 + 48),
							(int) (Math.random() * 3 + 4)));
					break;
				case 1: // right
					asteroids.add(new Asteroid(AsteroidGame.WIDTH + 16f, (float) Math.random() * AsteroidGame.HEIGHT,
							(float) (Math.random() * 90 + 225), (int) (Math.random() * 32 + 48),
							(int) (Math.random() * 3 + 4)));
					break;
				case 2: // top
					asteroids.add(new Asteroid((float) Math.random() * AsteroidGame.WIDTH, AsteroidGame.HEIGHT + 16f,
							(float) (Math.random() * 90 + 135), (int) (Math.random() * 32 + 48),
							(int) (Math.random() * 3 + 4)));
					break;
				case 3: // bottom
					asteroids.add(new Asteroid((float) Math.random() * AsteroidGame.WIDTH, -16f,
							(float) (Math.random() * 90 + 305), (int) (Math.random() * 32 + 48),
							(int) (Math.random() * 3 + 4)));
					break;
				}
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

		// Draw the explosions in red
//		shapeRenderer.setColor(1, 0.1f, 0.1f, 1);	<-- changes the colour of the explosions to red
		for (Explosion e : explosions) {
			float angle = 0;

			while (angle < 360) {
				shapeRenderer.line(e.getX(), e.getY(), e.getX() + MathUtils.sinDeg(angle) * e.getSize(),
						e.getY() + MathUtils.cosDeg(angle) * e.getSize());
				angle += e.getAngle();
			}
		}

		shapeRenderer.end();

		sb.setProjectionMatrix(camera.combined);
		sb.begin();

		// Draw the number of lives and score in top left corner
		font.draw(sb, "lives:  " + Integer.toString(lives), 32, AsteroidGame.HEIGHT - 32f);
		font.draw(sb, "score:  " + Integer.toString(score), 32, AsteroidGame.HEIGHT - 48f);

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
		shapeRenderer.dispose();
	}

	public void addPlayer(Player object) {
		// TODO
	}

	public void addAsteroid(Asteroid object) {
		asteroids.add(object);
	}

	public void addLaser(Laser object) {
		lasers.add(object);
	}

	public void addExplosion(Explosion object) {
		explosions.add(object);
	}

	public void updatePosition(UpdatePosition up) throws ArrayIndexOutOfBoundsException {
		switch (up.obj) {
		case UpdatePosition.PLAYER:
			// TODO
			break;
		case UpdatePosition.ASTEROID:
			asteroids.get(up.index).updatePositoin(up);
			break;
		case UpdatePosition.LASER:
			lasers.get(up.index).updatePosition(up);
			break;
		default:
			break;
		}
	}
}
