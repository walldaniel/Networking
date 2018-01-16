package com.wall.game.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.wall.game.AsteroidGame;

public class Laser {

	private final float[] VERTICES = { 0,0,4,0,4,16,0,16};
	
	// Used to determine when the laser has gone out of the screen
	public static final int REMOVAL_X = AsteroidGame.WIDTH + 160;
	public static final int REMOVAL_Y = AsteroidGame.HEIGHT + 16;

	public static final int WIDTH = 8;
	public static final int HEIGHT = 64;

	private final float speed = 1024f;

	private Polygon shape;

	public Laser(float x, float y, float d) {
		shape = new Polygon(VERTICES);
		shape.setPosition(x, y);
		shape.setRotation(d);
	}

	public Laser() {

	}
	
	public boolean update(float dt) {
		shape.translate(-dt * speed * MathUtils.sinDeg(shape.getRotation()), dt * speed * MathUtils.cosDeg(shape.getRotation()));
		
		return false;
	}
	
	public Polygon getShape() {
		return shape;
	}

	public double getDirectionInDegrees() {
		return shape.getRotation();
	}

	public float getX() {
		return shape.getX();
	}

	public float getY() {
		return shape.getY();
	}

}
