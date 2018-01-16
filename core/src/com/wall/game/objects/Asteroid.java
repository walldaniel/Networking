package com.wall.game.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.wall.game.AsteroidGame;

public class Asteroid {

	public static float SPEED = 64f;
	private int size;
	
	private Polygon shape;
	public final int numVertices;

	// Main constructor
	public Asteroid(float x, float y, float direction, int size, int numVertices) {
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

	public int getSize() {
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
		shape.translate(dt * 64f * MathUtils.sinDeg(shape.getRotation()), dt * 64f * MathUtils.cosDeg(shape.getRotation()));

		// Check if asteroid is out of bounds
		if (shape.getX() < -64f || shape.getX() > AsteroidGame.WIDTH + 64f || shape.getY() < -64f || shape.getY() > AsteroidGame.HEIGHT + 64f) {
			return true;
		}

		return false;
	}

	public Polygon getShape() {
		return shape;
	}
	
	public float[] getVertices() {
		return shape.getTransformedVertices();
	}

	public void setX(float x, float y) {
		shape.setPosition(x, y);
	}

	public void setDirection(float d) {
		shape.setRotation(d);
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

	public void translate(float x, float y) {
		shape.translate(x, y);
	}

}
