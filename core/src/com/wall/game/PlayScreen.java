package com.wall.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;
import com.wall.game.objects.Player.PlayerStats;

public class PlayScreen implements Screen {

	private final AsteroidGame game;
	private OrthographicCamera camera;

	private ShapeRenderer shapeRenderer;

	private HashMap<Integer, Player> players;
	private ArrayList<Laser> lasers;
	private ArrayList<Asteroid> asteroids;

	public Integer myPlayerindex;
	private boolean iteratingOverAsteroids = false;
	private Asteroid tempAsteroid;
	private Laser tempLaser;

	public PlayScreen(final AsteroidGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, AsteroidGame.WIDTH, AsteroidGame.HEIGHT);

		shapeRenderer = new ShapeRenderer();

		players = new HashMap<Integer, Player>();
		lasers = new ArrayList<Laser>();
		asteroids = new ArrayList<Asteroid>();
		Player player = new Player(32, 32);
		player.setPlayerNumber((short) game.client.getID());
		players.put(game.client.getID(), player);
		game.client.sendTCP(player);
		myPlayerindex = game.client.getID();
		game.client.sendTCP("GET_PLAYERS");
	}

	public void update(float dt) {
		// Get the user input
		if (myPlayerindex != null && players.containsKey(myPlayerindex)) {
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				players.get(myPlayerindex).addRotationalForce(dt * 32f);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				players.get(myPlayerindex).addRotationalForce(-dt * 32f);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				players.get(myPlayerindex).addForceForward(dt * 16f);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				players.get(myPlayerindex).addForceForward(-dt * 16f);
			}

			// If the space bar is pressed launch new bullet at the center of the sprite
			// TODO: fix the lasers from ship
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				Laser laser = new Laser(players.get(myPlayerindex).getX(), players.get(myPlayerindex).getY(),
						players.get(myPlayerindex).getDirectionInDegrees());
				lasers.add(laser);
				game.client.sendTCP(laser);
			}

		}

		// Update the location of the ships
		players.get(myPlayerindex).update();

		// Send clients ship location to server
		// TODO: Make it so only if the position is changed that it sends data
		game.client.sendTCP(new Player.PlayerStats(players.get(myPlayerindex).getX(), players.get(myPlayerindex).getY(),
				players.get(myPlayerindex).getDirectionInDegrees(), players.get(myPlayerindex).getPlayerNumber())
						.sendTcp());

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

		// Check if a laser or player has collided with an asteroid
		// Iterates over both arrays, code from:
		// https://stackoverflow.com/questions/18448671/how-to-avoid-concurrentmodificationexception-while-removing-elements-from-arr
		Iterator<Asteroid> iterAsteroids = asteroids.iterator();
		Iterator<Laser> iterLasers = lasers.iterator();
		iteratingOverAsteroids = true;
		long time = System.currentTimeMillis();
		while (iterAsteroids.hasNext()) {
			while (iterLasers.hasNext()) {
				if (Intersector.overlapConvexPolygons(iterAsteroids.next().getShape(), iterLasers.next().getShape())) {
					iterAsteroids.remove();
					iterLasers.remove();
				}
			}
		}
		System.out.println(System.currentTimeMillis() - time);
		iteratingOverAsteroids = false;
		
		// Check if an asteroid or laser was added
		if(tempAsteroid != null) {
			asteroids.add(tempAsteroid);
			tempAsteroid = null;
		}
		if(tempLaser != null) {
			lasers.add(tempLaser);
			tempLaser = null;
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	// Only add a player if their number isn't in the player map
	public void addPlayer(Player player) {
		if (!players.containsKey((int) player.getPlayerNumber())) {
			players.put((int) player.getPlayerNumber(), player);
			System.out.println("Adding player");
		}
	}

	public HashMap<Integer, Player> getPlayers() {
		return players;
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
		for (Player player : players.values()) {
			shapeRenderer.polygon(player.getShip().getTransformedVertices());
		}

		// Draw all the lasers
		for (Laser l : lasers) {
			shapeRenderer.polygon(l.getShape().getTransformedVertices());
		}

		// Draw the asteroids
		for (Asteroid a : asteroids) {
			shapeRenderer.polygon(a.getShape().getTransformedVertices());
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
		// shipTex.dispose();
	}

	public void updatePlayerPos(PlayerStats playerStats) {
		if (players.containsKey((int) playerStats.index)) {
			players.get((int) playerStats.index).setDirection(playerStats.direction);
			players.get((int) playerStats.index).setPosition(playerStats.x, playerStats.y);
		}
	}

	// Add a new asteroid or laser, usually from server
	// TODO: What happens if multiple asteroids spawn??
	public void addLaser(Laser laser) {
		// Check if iterating since could cause problems
		if(!iteratingOverAsteroids) {
			lasers.add(laser);
			return;
		}
		
		// Add the laser to temp storage
		tempLaser = laser;
	}
	public void addEnemy(Asteroid asteroid) {
		// Check if iterating over asteroids since if you add asteroid in middle will cause problems
		if(!iteratingOverAsteroids) {
			asteroids.add(asteroid);
			return;
		}
		
		// Add the asteroid to be added soon
		tempAsteroid = asteroid;
	}

}
