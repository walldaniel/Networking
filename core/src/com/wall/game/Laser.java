package com.wall.game;

public class Laser {
	
	public static final float REMOVAL_X = Game.WIDTH + 160;
	public static final float REMOVAL_Y = Game.HEIGHT + 16;
	
	private final float speed = 1024f;

	private float x, y;
	private double direction;	// in rads
	
	public Laser(float x, float y, double d) {
		this.x = x;
		this.y = y;
		this.direction = d;
	}
	
	public Laser() {
		
	}
	
	public void update(float dt) {
		x += -speed * dt * Math.sin(direction);
		y += speed * dt * Math.cos(direction);
	}
	
	public double getDirectionInRads() {
		return direction;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public static class LaserStat {
		public float x, y;
		public double direction;
		
		public LaserStat(float x, float y, double direction) {
			this.x = x;
			this.y = y;
			this.direction = direction;
		}
		
		public LaserStat() {
			
		}
	}

	public LaserStat toLaserStat() {
		return new LaserStat(x, y, direction);
	}
}
