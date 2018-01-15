package com.wall.game.objects;

public class Asteroid {

	public static float SPEED = 64f;

	private float x, y;
	private double direction;

	private Vector2[] vertices;
	public final int numVertices;

	// Main constructor
	public Asteroid(float x, float y, double direction, int size, int numVertices) {
		this.numVertices = numVertices;
		this.x = x;
		this.y = y;

		vertices = new Vector2[numVertices];

		// Generates an asteroid with random vertices with lines connected between them
		switch (numVertices) {
		case 4: // 4 vertices or sides, etc..
			vertices[0] = new Vector2((float) (Math.random() * (size / 2) - 8),
					(float) (Math.random() * (size / 2) - 8));
			vertices[1] = new Vector2((float) (Math.random() * (size / 2) + 8) + (size / 2),
					(float) (Math.random() * (size / 2) - 8));
			vertices[3] = new Vector2((float) (Math.random() * (size / 2) - 8),
					(float) (Math.random() * (size / 2) + 8) + (size / 2));
			vertices[2] = new Vector2((float) (Math.random() * (size / 2) + 8) + (size / 2),
					(float) (Math.random() * (size / 2) + 8) + (size / 2));
			break;
		case 5:
			vertices[0] = new Vector2((float) (Math.random() * (size / 3)), (float) (Math.random() * size / 2 - 8));
			vertices[1] = new Vector2((float) (Math.random() * (size / 3) + (size / 3)),
					(float) (Math.random() * size / 2 - 8));
			vertices[2] = new Vector2((float) (Math.random() * (size / 3)) + (2 * size / 3),
					(float) (Math.random() * size));
			vertices[3] = new Vector2((float) (Math.random() * (size / 3) + (size / 3)),
					(float) (Math.random() * size / 2 + (size / 2)));
			vertices[4] = new Vector2((float) (Math.random() * (size / 3)),
					(float) (Math.random() * size / 2 - 8) + (size / 2));
			break;
		case 6:
			vertices[0] = new Vector2((float) (Math.random() * (size / 4)), (float) (Math.random() * size / 2 - 8));
			vertices[1] = new Vector2((float) (Math.random() * (size / 4) + (size / 3)),
					(float) (Math.random() * size / 2 - 8));
			vertices[2] = new Vector2((float) (Math.random() * (size / 4) + (size / 1.5)),
					(float) (Math.random() * size / 2 - 8));
			vertices[3] = new Vector2((float) (Math.random() * (size / 4) + (size / 1.5)),
					(float) (Math.random() * size / 2 + (size / 2 + 8)));
			vertices[4] = new Vector2((float) (Math.random() * (size / 4) + (size / 3)),
					(float) (Math.random() * size / 2 + (size / 2 + 8)));
			vertices[5] = new Vector2((float) (Math.random() * size / 4),
					(float) (Math.random() * size / 2 + (size / 2 + 8)));
			break;
		default:
			System.out.println("Error");
		}
	}

	public Asteroid(float x, float y, double direction, Vector2[] vertices) {
		this.x = x;
		this.y = y;

		this.vertices = vertices;
		this.numVertices = vertices.length;
	}

	public Asteroid() {
		numVertices = -1;
	}

	public Vector2 getVertice(int i) {
		return vertices[i];
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setDirection(double d) {
		this.direction = d;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public double getDirection() {
		return direction;
	}

	public void addX(float x) {
		this.x += x;
	}

	public void addY(float y) {
		this.y += y;
	}
}
