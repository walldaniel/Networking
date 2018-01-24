package com.wall.game.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.wall.game.AsteroidGame;

public class Asteroid extends Shape {

	public static float SPEED = 64f;
	private float size;
	
	public final int numVertices;

	// Main constructor
	public Asteroid(float x, float y, float direction, float size, int numVertices) {
		this.numVertices = numVertices;
		this.size = size;

		float[] vertices = new float[numVertices * 2];
		// Generates an asteroid with random vertices, each vertice is a pair
		switch (numVertices) {
		case 4: // 4 vertices or sides, etc..
			vertices[0] = (float) (Math.random() * (size / 2) - 8);
			vertices[1] = (float) (Math.random() * (size / 2) - 8);
			vertices[2] = (float) (Math.random() * (size / 2) + 8) + (size / 2);
			vertices[3] = (float) (Math.random() * (size / 2) - 8);
			vertices[4] = (float) (Math.random() * (size / 2) + 8) + (size / 2);
			vertices[5] = (float) (Math.random() * (size / 2) + 8) + (size / 2);
			vertices[6] = (float) (Math.random() * (size / 2) - 8);
			vertices[7] = (float) (Math.random() * (size / 2) + 8) + (size / 2);
			break;
		case 5:
			vertices[0] = (float) (Math.random() * (size / 3));
			vertices[1] = (float) (Math.random() * size / 2 - 8);
			vertices[2] = (float) (Math.random() * (size / 3) + (size / 3));
			vertices[3] = (float) (Math.random() * size / 2 - 8);
			vertices[4] = (float) (Math.random() * (size / 3)) + (2 * size / 3);
			vertices[5] = (float) (Math.random() * size);
			vertices[6] = (float) (Math.random() * (size / 3) + (size / 3));
			vertices[7] = (float) (Math.random() * size / 2 + (size / 2));
			vertices[8] = (float) (Math.random() * (size / 3));
			vertices[9] = (float) (Math.random() * size / 2 - 8) + (size / 2);
			break;
		case 6:
			vertices[0] = (float) (Math.random() * (size / 4));
			vertices[1] = (float) (Math.random() * size / 2 - 8);
			vertices[2] = (float) (Math.random() * (size / 4) + (size / 3));
			vertices[3] = (float) (Math.random() * size / 2 - 8);
			vertices[4] = (float) (Math.random() * (size / 4) + (size / 1.5));
			vertices[5] = (float) (Math.random() * size / 2 - 8);
			vertices[6] = (float) (Math.random() * (size / 4) + (size / 1.5));
			vertices[7] = (float) (Math.random() * size / 2 + (size / 2 + 8));
			vertices[8] = (float) (Math.random() * (size / 4) + (size / 3));
			vertices[9] = (float) (Math.random() * size / 2 + (size / 2 + 8));
			vertices[10] = (float) (Math.random() * size / 4);
			vertices[11] = (float) (Math.random() * size / 2 + (size / 2 + 8));
			break;
		default:
			System.out.println("Error");
		}
		
		// Create the polygon of the asteroid with generated vertices
		shape = new Polygon(vertices);
		shape.setPosition(x, y);
		shape.setRotation(direction);
	}

	public float getSize() {
		return size;
	}
	
	public Asteroid(float x, float y, float direction, float[] vertices) {
		this.numVertices = vertices.length;

		shape = new Polygon(vertices);
		shape.setPosition(x, y);
		shape.setRotation(direction);
	}

	public Asteroid() {
		numVertices = -1;
	}

	// Update the position of the asteroid
	// Returns true if out of bounds, so that it can be deleted
	public boolean update(float dt) {
		// Update the position of asteroid
		shape.translate(dt * 6000 * (1/ (float) size) * MathUtils.sinDeg(shape.getRotation()), dt * 6000 * (1/ (float) size) * MathUtils.cosDeg(shape.getRotation()));

		// Check if asteroid is out of bounds
		if (shape.getX() < -64f || shape.getX() > AsteroidGame.WIDTH + 64f || shape.getY() < -64f || shape.getY() > AsteroidGame.HEIGHT + 64f) {
			return true;
		}

		return false;
	}
	
	public float[] getVertices() {
		return shape.getTransformedVertices();
	}

}
