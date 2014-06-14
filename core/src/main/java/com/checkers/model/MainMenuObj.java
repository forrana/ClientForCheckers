package com.checkers.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MainMenuObj {
	//VAR
		public static final float SIZE = 2f;
		
		Vector2 	position = new Vector2();
		Rectangle 	bounds = new Rectangle();
		boolean 	color;	//black = true
		
		/**constructor**/
		public MainMenuObj(Vector2 pos) {
			this.position = pos;
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
