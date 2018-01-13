package com.wall.game.objects;

import com.badlogic.gdx.math.Polygon;

public class Player {
	
	private short playerNumber;

	private float x, y;
	private double direction;

	private Polygon shape;
	
	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		direction = 0;
	}

	public Player() {

	}

	public void setPlayerNumber(short playerNumber) {
		this.playerNumber = playerNumber;
	}

	public short getPlayerNumber() {
		return playerNumber;
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

	public static class PlayerStats {
		public float x, y;
		public double direction;
		public short index;

		public PlayerStats(float x, float y, double direction, short index) {
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
				direction = Double.parseDouble(data[2]);
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