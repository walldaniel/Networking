package com.wall.game;

public class Enemy {

	public static float SPEED = 32f;
	
	private float x, y;
	private double direction;
	
	public Enemy(float x, float y, double direction) {
		this.x = x;
		this.y = y;
	}
	
	public Enemy() {
		
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
