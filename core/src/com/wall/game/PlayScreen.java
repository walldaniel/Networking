package com.wall.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;
import com.wall.game.objects.Laser.LaserStat;
import com.wall.game.objects.Player.PlayerStats;

public class PlayScreen implements Screen {

	private final Game game;
	private OrthographicCamera camera;

	// private Texture shipTex;
	// private Sprite shipSprite;

	// private Texture laserTex;
	// private Sprite laserSprite;

	// private Texture enemyTex;
	// private Sprite enemySprite;

	private ShapeRenderer shapeRenderer;

	private HashMap<Integer, Player> players;
	private ArrayList<Laser> lasers;
	private ArrayList<Asteroid> asteroids;

	public Integer myPlayerindex;

	public PlayScreen(final Game game, int myPlayerIndex) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// shipTex = new Texture("ship.png");
		// shipSprite = new Sprite(shipTex);
		// laserTex = new Texture("laser.png");
		// laserSprite = new Sprite(laserTex);
		// enemyTex = new Texture("enemy.png");
		// enemySprite = new Sprite(enemyTex);

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
				Laser laser = new Laser(players.get(myPlayerindex).getX(), players.get(myPlayerindex).getY(), players.get(myPlayerindex).getDirectionInDegrees());
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
			if(lasers.get(i).update(dt)) {
				lasers.remove(i);
				i--;
			}
		}

		// Update all the asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			// Also checks if the asteroid is out of bounds to delete it
			if (asteroids.get(i).update(dt)) {
				asteroids.remove(i);

				// If the asteroid is removed make sure that you noe check the current i by decrementing it
				i--;
			}
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
		for(Laser l : lasers) {
			shapeRenderer.polygon(l.getShape().getTransformedVertices());
		}

		// Draw the asteroid with lines between each vertice
		// The last line has to be drawn from last vertice to first to complete the shape
		for (int i = 0; i < asteroids.size(); i++) {
			for (int j = 0; j < asteroids.get(i).numVertices; j++) {
				if (j < asteroids.get(i).numVertices - 1)
					shapeRenderer.line(asteroids.get(i).getVertice(j).x + asteroids.get(i).getX(),
							asteroids.get(i).getVertice(j).y + asteroids.get(i).getY(),
							asteroids.get(i).getVertice(j + 1).x + asteroids.get(i).getX(),
							asteroids.get(i).getVertice(j + 1).y + asteroids.get(i).getY());
				else
					shapeRenderer.line(asteroids.get(i).getVertice(j).x + asteroids.get(i).getX(),
							asteroids.get(i).getVertice(j).y + asteroids.get(i).getY(),
							asteroids.get(i).getVertice(0).x + asteroids.get(i).getX(),
							asteroids.get(i).getVertice(0).y + asteroids.get(i).getY());
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
		// shipTex.dispose();
	}

	public void updatePlayerPos(PlayerStats playerStats) {
		if (players.containsKey((int) playerStats.index)) {
			players.get((int) playerStats.index).setDirection(playerStats.direction);
			players.get((int) playerStats.index).setPosition(playerStats.x, playerStats.y);
		}
	}

	public void addLaser(LaserStat laser) {
		lasers.add(new Laser(laser.x, laser.y, laser.direction));
	}

	public void addEnemy(Asteroid enemy) {
		asteroids.add(enemy);
	}

}
