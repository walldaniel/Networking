package com.wall.game;

public class Player {

	public final int turnLeft, turnRight;
	public final int forward, backward;
	public final int shoot;

	private float x, y;
	private double direction;

	public Player(float x, float y, int left, int right, int forward, int backward, int shoot) {
		this.x = x;
		this.y = y;
		direction = 0;

		turnLeft = left;
		turnRight = right;
		this.forward = forward;
		this.backward = backward;
		this.shoot = shoot;
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

	public void addX(float x) {
		this.x += x;
	}

	public void addY(float y) {
		this.y += y;
	}

	public void addDirection(double d) {
		this.direction += d;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public double getDirectionInRads() {
		return direction;
	}

	public static class playerStats {
		public float x, y;
		public double direction;

		public playerStats(float x, float y, double direction) {
			this.x = x;
			this.y = y;
			this.direction = direction;
		}
	}
}
