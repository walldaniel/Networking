package com.wall.game.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.wall.game.Game;

public class Player {

	private final float[] VERTICES = { 0f, 0f, 16f, 32f, 32f, 0f, 16f, 8f };

	private short playerNumber;

	private Polygon shape;

	private Vector2 forces;
	private float rotationalForce; // +ve is right

	public Player(float x, float y) {
		shape = new Polygon(VERTICES);
		shape.setPosition(x, y);
		shape.setOrigin(x, y);
		shape.setRotation(0);

		forces = new Vector2(0f, 0f);
		rotationalForce = 0f;
	}

	public Player() {

	}

	public void setPlayerNumber(short playerNumber) {
		this.playerNumber = playerNumber;
	}

	public short getPlayerNumber() {
		return playerNumber;
	}

	public Polygon getShip() {
		return shape;
	}

	public void setDirection(float degrees) {
		shape.setRotation(degrees);
	}

	public void setPosition(float x, float y) {
		shape.setPosition(x, y);
	}

	// Update the position of the ship based on the forces applied to it
	public void update() {

		shape.rotate(rotationalForce);
		shape.translate(forces.x, forces.y);

		// Decrease the force on the ship
		forces.x *= 0.96f;
		forces.y *= 0.96f;
		rotationalForce *= 0.9f;
		
		// Check if out of bounds
		// TODO: Edit this so that it depends on the rotation of the player
		if(shape.getX() <= 0) {
			shape.setPosition(0, shape.getY());
			forces.x = 0;
		}
		if(shape.getX() >= Game.WIDTH) {
			shape.setPosition(Game.WIDTH, shape.getY());
			forces.x = 0;
		}
		if(shape.getY() <= 0) {
			shape.setPosition(shape.getX(), 0);
			forces.y = 0;
		}
		if(shape.getY() >= Game.HEIGHT) {
			shape.setPosition(shape.getX(), Game.HEIGHT);
			forces.y = 0;
		}
	}

	// A force either forward or backwards
	// Don't need a max force because the speed gradually decreases
	public void addForceForward(float force) {
		forces.x += -force * (float) MathUtils.sinDeg(shape.getRotation());
		forces.y += force * (float) MathUtils.cosDeg(shape.getRotation());
	}

	// A force either left or right
	public void addRotationalForce(float force) {
		if (rotationalForce < 32f && rotationalForce > -32f) {
			rotationalForce += force;
		}
	}

	public void adddPosition(float x, float y) {
		shape.setPosition(shape.getX() + x, shape.getY() + y);
	}

	public void addDirection(float degrees) {
		shape.rotate(degrees);
	}

	public float getX() {
		return shape.getX();
	}

	public float getY() {
		return shape.getY();
	}

	public float getDirectionInDegrees() {
		return shape.getRotation();
	}

	public static class PlayerStats {
		public float x, y;
		public float direction;
		public short index;

		public PlayerStats(float x, float y, float direction, short index) {
			this.x = x;
			this.y = y;
			this.direction = direction;
			this.index = index;
		}

		public PlayerStats() {
			x = 0f;
			y = 0f;
			direction = 0;
		}

		public PlayerStats(String s) {
			String[] data = s.split(",");
			try {
				x = Float.parseFloat(data[0]);
				y = Float.parseFloat(data[1]);
				direction = Float.parseFloat(data[2]);
				index = Short.parseShort(data[3]);
			} catch (NumberFormatException e) {
				System.out.println("Error reading the player data");
			}
		}

		public String sendTcp() {
			return x + "," + y + "," + direction + "," + index;
		}

	}
}
