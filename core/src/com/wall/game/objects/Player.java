package com.wall.game.objects;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Player {
	
	private final float[] VERTICES = { 0f, 0f, 16f, 32f, 32f, 0f, 16f, 8f };
	
	private short playerNumber;

	private Polygon shape;
	
	private Vector2 forces;
	private float rotationalForce;	// +ve is right
	
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
		System.out.println(forces.x + " - " + forces.y);
		
		shape.translate(forces.x, forces.y);
		shape.rotate(rotationalForce);
		
		// Decrease the force on the ship
		forces.x *= 0.95f;
		forces.y *= 0.95f;
		rotationalForce *= 0.85f;
	}
	
	// A force either forward or backwards
	public void addForceForward(float force) {
		if(forces.x < 3f) {
			forces.x += force * (float) Math.sin(Math.toRadians(shape.getRotation()));
		}
		if(forces.y < 3f) {
			forces.y += force * (float) Math.cos(Math.toRadians(shape.getRotation()));
		}
	}
	// A force either left or right
	public void addRotationalForce(float force) {
		if(rotationalForce < 10f && rotationalForce > -10f) {
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
