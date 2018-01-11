package com.wall.game.objects;


public class Enemy {

	public static float SPEED = 32f;
	
	private float x, y;
	private double direction;
	
	private Vector2[] vertices;
	public final int numVertices;
	
	public Enemy(float x, float y, double direction, Vector2[] vertices) {
		this.x = x;
		this.y = y;
		
		this.vertices = vertices;
		this.numVertices = vertices.length;
	}
	
	public Enemy(float x, float y, double direction, float[] xVer, float[] yVer) {
		this.x = x;
		this.y = y;
		
		vertices = new Vector2[xVer.length];
		for(int i = 0; i < xVer.length; i++) {
			vertices[i] = new Vector2(xVer[i], yVer[i]);
		}
		numVertices = vertices.length;
	}
	
	public Enemy() {
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
