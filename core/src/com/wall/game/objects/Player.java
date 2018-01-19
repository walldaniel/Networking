package com.wall.game.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.wall.game.AsteroidGame;

public class Player {

	private final float[] VERTICES = { 0f, 0f, 16f, 32f, 32f, 0f, 16f, 8f };

	private int playerNumber;

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

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public int getPlayerNumber() {
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
		rotationalForce *= 0.86f;
		
		// Check if out of bounds
		
		// TODO: Edit this so that it depends on the rotation of the player
		if(shape.getX() <= 0) {
			shape.setPosition(0, shape.getY());
			forces.x = 0;
		}
		if(shape.getX() >= AsteroidGame.WIDTH) {
			shape.setPosition(AsteroidGame.WIDTH, shape.getY());
			forces.x = 0;
		}
		if(shape.getY() <= 0) {
			shape.setPosition(shape.getX(), 0);
			forces.y = 0;
		}
		if(shape.getY() >= AsteroidGame.HEIGHT) {
			shape.setPosition(shape.getX(), AsteroidGame.HEIGHT);
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
}
