package com.checkers.model;
/**
 * 
 */
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
/**
 * 
 * @author forrana
 * This class released model class from MVC pattern.
 * It's class container for cells and checkers
 * It's have initialize board method and various getters  
 */
public class Board {

	//Board cells array
	Array<Cell> cells = new Array<Cell>();
	Array<Checker> checkers = new Array<Checker>();	
	//private
	public static float ppuX;
	public static float ppuY;
	private static final float BOARD_BOTTOM = 0.5f;
	// Constructor
		public Board(){
			createBoard();
		}
		//--------------------------------------
		//Initializer
		private void createBoard() {
		
			boolean black = true;
			boolean flag = true;
			//desk's loop
			//place white cheker's
			 for(float i = BOARD_BOTTOM; i < BOARD_BOTTOM + 3f; i++){
				 for(float j = BOARD_BOTTOM;j < BOARD_BOTTOM + 8f; j = j+2){
					 if(flag)checkers.add(new Checker(new Vector2(j,i), !black, checkers.size));
					 	else checkers.add(new Checker(new Vector2(j+1,i), !black, checkers.size));
				 }	 	
				 flag = !flag;
			 }
			 
			 //place black checker's
			
			 for(float i = BOARD_BOTTOM + 5f; i < BOARD_BOTTOM + 8f; i++){
				 for(float j = BOARD_BOTTOM;j < BOARD_BOTTOM + 8f; j = j+2){
					 if(flag)checkers.add(new Checker(new Vector2(j,i), black, checkers.size));
					 	else checkers.add(new Checker(new Vector2(j+1,i), black, checkers.size));
				 }	 
				 flag = !flag;
			 }
					
			 for(float j = BOARD_BOTTOM; j < BOARD_BOTTOM + 8f; j++){		 		 
		        	for(float i = BOARD_BOTTOM; i < BOARD_BOTTOM + 8f; i++){  			        	        		          	      		
		     	        if(black){
		     	        	cells.add(new Cell(new Vector2(j,i), black));
		        			black = !black;
		        		}
		        				else{
		        					cells.add(new Cell(new Vector2(j,i), black));
		        					black = !black;
		        				} 		  		
		        	}	 
		        black = !black;
			}				
		}
	// Getters 
	/**
	 * @return board start coord
	 */
	public float getBoardBottom(){
		return BOARD_BOTTOM;
	}
	/**
	 * 
	 * @return Array of board cells
	 */
	public Array<Cell> getCells() {
			return cells;
	}
	/**
	 * 
	 * @return Array of board checkers
	 */
	public Array<Checker> getCheckers(){
		return checkers;
	}
	
	public void delChecker(Checker check){
		checkers.removeValue(check, true);
	}
	
	public void delChecker(int index){
		checkers.removeIndex(index);	
	}
	
	public Cell getCellByDeskCoord(float x, float y){
		float tX;
		float tY;
		tX = x / ppuX;
		tY = y / ppuY;
		
		tY = tY -  (8f + this.BOARD_BOTTOM);
		tY = Math.abs(tY);
		
		//for(Cell cell1 : cells){
        Cell cell1;
        for(int i=0;i < cells.size;i++){
            cell1 = cells.get(i);
			if((tX >= cell1.getPosition().x) && (tX <= cell1.getBounds().width + cell1.getPosition().x))
				if((tY >= cell1.getPosition().y) && (tY <= cell1.getBounds().height + cell1.getPosition().y))
					return cell1;	
		}
		return null;
	}
	
	public Cell getCellByGLCoord(float x, float y) {
			
			//for(Cell cell : cells){
            Cell cell;
            for(int i=0;i < cells.size;i++){
            cell = cells.get(i);
				if((x == cell.getPosition().x))
					if((y == cell.getPosition().y))			
						return cell;	
			}	
			return null;
		}
	
	public Checker getCheckerByGLCoord(float tX, float tY){
		//for(Checker checker : checkers){
        Checker checker;
        for(int i=0; i < checkers.size;i++){
                checker = checkers.get(i);
			if((tX == checker.getPosition().x))
				if((tY == checker.getPosition().y))
					return checker;
		}
		return null;
		
	}
	
	public Checker getCheckerByGLCoord(Vector2 pos){
		//for(Checker checker : checkers){
        Checker checker;
        for(int i=0; i < checkers.size;i++){
                checker = checkers.get(i);
			if(pos.x == checker.getPosition().x)
				if(pos.y == checker.getPosition().y)
					return checker;	
		}
		return null;
		
	}
	
	public Checker getCheckerByDeskCoord(float x, float y){
		float tX;
		float tY;
		tX = x / ppuX;
		tY = y / ppuY;
		
		tY = tY -  (8f + this.BOARD_BOTTOM);
		tY = Math.abs(tY);
		
//		for(Checker checker : checkers){
        Checker checker;
        for(int i=0; i < checkers.size;i++){
            checker = checkers.get(i);
			if((tX >= checker.getPosition().x) && (tX <= checker.getBounds().width + checker.getPosition().x))
				if((tY >= checker.getPosition().y) && (tY <= checker.getBounds().height + checker.getPosition().y))
					return checker;	
		}
		return null;
	}
	
	public Checker getCheckerByDeskCoord(Vector2 inpCoord){
		float tX;
		float tY;
		tX = inpCoord.x / ppuX;
		tY = inpCoord.y / ppuY;
		
		tY = tY - (8f + this.BOARD_BOTTOM);
		tY = Math.abs(tY);
		
//		for(Checker checker : checkers){
        Checker checker;
        for(int i=0; i < checkers.size;i++){
            checker = checkers.get(i);
			if((tX >= checker.getPosition().x) && (tX <= checker.getBounds().width + checker.getPosition().x))
				if((tY >= checker.getPosition().y) && (tY <= checker.getBounds().height + checker.getPosition().y))
					return checker;
		}
		return null;
	}
		
}
