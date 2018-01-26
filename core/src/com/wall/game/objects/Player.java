package com.wall.game.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.wall.game.AsteroidGame;

public class Player extends Shape {
	// The placement of the vertices of the ship when it is spawned
	private final float[] VERTICES = { 0f, 0f, 16f, 32f, 32f, 0f, 16f, 8f };

	private Vector2 forces;
	private float rotationalForce; // +ve is right

	public Player(float x, float y) {
		shape = new Polygon(VERTICES);
		shape.setPosition(x, y);
		shape.setOrigin(16, 24); // Sets the point of rotation to the front of the ship
		shape.setRotation(0);

		forces = new Vector2(0f, 0f);
		rotationalForce = 0f;
	}

	// Update the position of the ship based on the forces applied to it
	public void update(float dt) {
		System.out.println(forces + " - " + rotationalForce);
		
		// CHeck if the force is to high
		if(forces.x > 8f)
			forces.x = 8;
		if(forces.x < -8f)
			forces.x = -8f;
		if(forces.y > 8f)
			forces.y = 8f;
		if(forces.y < -8f)
			forces.y = -8f;
		if(rotationalForce > 8f)
			rotationalForce = 8f;
		if(rotationalForce < -8f)
			rotationalForce = -8f;

		shape.rotate(rotationalForce);
		shape.translate(forces.x * dt, forces.y * dt);

		// Decrease the force on the ship
		forces.x *= 0.96f * dt;
		forces.y *= 0.96f * dt;
		rotationalForce *= 0.86f * dt;

		// Check if out of bounds
		// TODO: Get this working correctly, need to add depending on the rotation of ship
		if (shape.getX() <= 0) {
			shape.setPosition(0, shape.getY());
			forces.x = 0;
		}
		if (shape.getX() >= AsteroidGame.WIDTH) {
			shape.setPosition(AsteroidGame.WIDTH, shape.getY());
			forces.x = 0;
		}
		if (shape.getY() <= 0) {
			shape.setPosition(shape.getX(), 0);
			forces.y = 0;
		}
		if (shape.getY() >= AsteroidGame.HEIGHT) {
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
		rotationalForce += force;
	}
}
