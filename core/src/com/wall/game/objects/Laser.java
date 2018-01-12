package com.wall.game.objects;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.wall.game.Game;

public class Laser {

	public static final float REMOVAL_X = Game.WIDTH + 160;
	public static final float REMOVAL_Y = Game.HEIGHT + 16;

	public static final int WIDTH = 8;
	public static final int HEIGHT = 64;

	private final float speed = 1024f;

	private float direction; // in rads

	private Polygon shape;

	public Laser(float x, float y, float d) {
		shape = new Polygon(new float[] { x, y, x + WIDTH, y, x + WIDTH, y + HEIGHT, x, x + HEIGHT });
		shape.setOrigin(x + WIDTH / 2, y + HEIGHT / 2);
		shape.setRotation(d);

		this.direction = d;
	}

	public Laser() {

	}

	public Polygon getShape() {
		return shape;
	}
	
	public void update(float dt) {
		shape.setPosition((float) (shape.getX() + (-speed * dt * Math.sin(direction))),
				(float) (shape.getY() + (speed * dt * Math.cos(direction))));
		System.out.println(shape.getX() + " - " + shape.getY());
	}

	public double getDirectionInRads() {
		return shape.getRotation();
	}

	public float getX() {
		return shape.getX();
	}

	public float getY() {
		return shape.getY();
	}

	public static class LaserStat {
		public float x, y;
		public float direction;

		public LaserStat(float x, float y, float direction) {
			this.x = x;
			this.y = y;
			this.direction = direction;
		}

		public LaserStat() {

		}
	}

	public LaserStat toLaserStat() {
		return new LaserStat(shape.getX(), shape.getY(), direction);
	}
}
