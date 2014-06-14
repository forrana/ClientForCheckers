package com.checkers.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Checker {

		//VAR
		public static final float SIZE = 1f;
		
		Vector2 	position = new Vector2();
		Rectangle 	bounds = new Rectangle();
		boolean 	color;	//false = black
		boolean		selected;
		boolean		isQueen;
        boolean     isKilled;
        boolean     canFight;
		int			index;
		
		/**constructor**/
		public Checker(Vector2 pos, boolean inColor, int ind) {
			this.index = ind;
			this.position = pos;
			this.color = inColor;
			this.selected = false;
			this.bounds.width = SIZE;
			this.bounds.height = SIZE;
			this.isQueen = false;
            this.isKilled = false;
            this.canFight = false;
		}

        public boolean getCanFight(){
            return canFight;
        }

        public void setCanFight(){
            canFight = !canFight;
        }

        public boolean getIsKilled(){
            return isKilled;
        }

        public void setIsKilled(){
            isKilled = true;
        }

        public void clearIsKilled(){
            isKilled = false;
        }


    public int getIndex(){
			return index;
		}
		
		public Vector2 getPosition() {
			return position;
		}
		
		public void setPosition(Vector2 pos){
			this.position = pos;
		}

		public Rectangle getBounds() {
			return bounds;
		}
		
		public void setQueen(){
			isQueen = true;
		}
		
		public boolean getQueen(){
			
			return isQueen;
		}
		
		public boolean getSelected(){
			return selected;
		}
		
		public void setSelected(){
			selected = !selected; 
		}
		
		public boolean getColor(){
			return color;
		}
			
}
	
