package com.wall.game.objects;

public class UpdatePosition {

	public static final int PLAYER = 0;
	public static final int ASTEROID = 1;
	public static final int LASER = 2;
	public static final int EXPLOSION = 3;

	
	public float x, y, direction;
	public int obj, index;

	public UpdatePosition(float x, float y, float direction, int obj, int index) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.obj = obj;
		this.index = index;
	}

	public UpdatePosition() {
		
	}
}
