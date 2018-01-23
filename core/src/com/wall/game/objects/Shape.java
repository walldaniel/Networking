package com.wall.game.objects;

import com.badlogic.gdx.math.Polygon;

// Contains what is needed for a basic shape/object that can be drawn to the sc
public abstract class Shape {

	protected Polygon shape;
	
	public Polygon getShape() {
		return shape;
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
	
	public void setDirection(float degrees) {
		shape.setRotation(degrees);
	}
	public void setPosition(float x, float y) {
		shape.setPosition(x, y);
	}
	public void setShape(Polygon p) {
		shape = p;
	}
	
	
	public void translate(float x, float y) {
		shape.translate(x, y);
	}
	public void addDirection(float degrees) {
		shape.rotate(degrees);
	}

	
}
