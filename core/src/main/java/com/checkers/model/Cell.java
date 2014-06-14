package com.checkers.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Cell {

	//VAR
	public static final float SIZE = 1f;
	
	Vector2 	position = new Vector2();
	Rectangle 	bounds = new Rectangle();
	boolean 	color;	//black = true
	
	/**constructor**/
	public Cell(Vector2 pos, boolean inColor) {
		this.position = pos;
		this.color = inColor;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}
	
	//Getters
	
	public Vector2 getPosition() {
		return position;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public boolean getColor(){
		return color;
	}
	
	
}
