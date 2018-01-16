package com.wall.game.objects;

// Displayed after an asteroid is destroyed
public class Explosion {

	private float x, y;
	private float angle;
	private float size;
	
	public Explosion(float x, float y) {
		this.x = x;
		this.y = y;
		size = 1;
		
		angle = (float) (Math.random() * 18 + 18);
	}
	
	// Update the size of the explosion
	// Once explosion has grown too big, delete it
	public boolean update() {
		size *= 1.5f;
		
		if(size > 32) 
			return true;
		
		return false;
	}
	
	public float getAngle() {
		return angle;
	}
	public float getSize() {
		return size;
	}
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
}
