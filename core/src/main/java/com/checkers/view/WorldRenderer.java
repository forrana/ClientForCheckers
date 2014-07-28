package com.checkers.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.checkers.controllers.MoveValidator;
import com.checkers.model.Board;
import com.checkers.model.Cell;
import com.checkers.model.Checker;


public class WorldRenderer {

    private static final float CAMERA_WIDTH = 8.5f;
	private static final float CAMERA_HEIGHT = 8.5f;
	
	private Board board;
	private OrthographicCamera cam;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	/** Textures **/

	private TextureRegion cellBlackTexture;
	private TextureRegion cellWhiteTexture;
	private TextureRegion checkerBlackTexture;
	private TextureRegion checkerWhiteTexture;
	private TextureRegion checkerBlackSelTexture;
	private TextureRegion checkerWhiteSelTexture;
	private TextureRegion checkerBlackQueenTexture;
	private TextureRegion checkerWhiteQueenTexture;
 	private TextureRegion boardBottomLetters[] = new TextureRegion[8];
 	private TextureRegion boardLeftNumbers[]   = new TextureRegion[8];

    Matrix4 mx4RotateBoard = new Matrix4();
	private SpriteBatch spriteBatch;
	private boolean debug = true;
	private int width;
	private int height;
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		board.ppuX = ppuX;
		board.ppuY = ppuY;
	}
	
	public WorldRenderer(Board board, boolean debug) {
		this.board = board;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		//this.cam.rotate(180);
		this.cam.position.set(CAMERA_WIDTH / 2f, (CAMERA_HEIGHT / 2f), 0);
		//this.cam.rotate(180);
        this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		loadTextures();
        //mx4RotateBoard.setToRotation(new Vector3(8,8,0), 180);
	}
	
	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.pack"));
		cellBlackTexture = atlas.findRegion("black");
		cellWhiteTexture = atlas.findRegion("white");
		checkerBlackTexture = atlas.findRegion("checker-black");
		checkerWhiteTexture = atlas.findRegion("checker-white");
		checkerBlackSelTexture = atlas.findRegion("checker-black-sel");
		checkerWhiteSelTexture = atlas.findRegion("checker-white-sel");
		checkerBlackQueenTexture = atlas.findRegion("checker-black-queen");
		checkerWhiteQueenTexture = atlas.findRegion("checker-white-queen");
		for(int i = 0; i <= 7;i++){
			String s1 = new String();
			s1 += (char)(i+'A');
			boardBottomLetters[i]	 = atlas.findRegion(s1);
			s1 = "n";
			s1 += Integer.toString(i+1);	
			boardLeftNumbers[i]		 = atlas.findRegion(s1);
			s1 = "";
		}
	}
	
	
	public void render() {

        spriteBatch.setTransformMatrix(mx4RotateBoard);
        spriteBatch.begin();
			drawCells();
			drawChecker();
			drawBorder();
			
		spriteBatch.end();
		if (!debug)
			drawDebug();
	}
	public void selChecker(float posX, float posY){

	//	Checker checker = board.getCheckerByCoord(posX, posY);	
	//	if(checker != null)checker.setSelected();
		
	}

	private void drawCells() {
        //MoveValidator.isPlayerWhite
        for (Cell cell : board.getCells()) {
			if(cell.getColor()){
			//	spriteBatch.draw(cellBlackTexture, cell.getPosition().x * ppuX,
    		//				cell.getPosition().y * ppuY, Cell.SIZE * ppuX, Cell.SIZE * ppuY);
                spriteBatch.draw(cellBlackTexture, cell.getPosition().x * ppuX,
                        cell.getPosition().y * ppuY, Cell.SIZE * ppuX, Cell.SIZE * ppuY);
			}	
			else spriteBatch.draw(cellWhiteTexture, cell.getPosition().x * ppuX, 
					    cell.getPosition().y * ppuY, Cell.SIZE * ppuX, Cell.SIZE * ppuY);
		}
	}

	private void drawBorder(){
        if(MoveValidator.isPlayerWhite)
            for(int i = 0; i < 8; i++){
                spriteBatch.draw(boardBottomLetters[i], (i+0.5f) * ppuX,
                        0f * ppuY, 1f * ppuX, 0.5f * ppuY);
                spriteBatch.draw(boardLeftNumbers[i], 0.0f * ppuX,
                        (i + 0.5f) * ppuY, 0.5f * ppuX, 1f * ppuY);
		}
        else
            for(int i = 0; i < 8 ; i++){
                spriteBatch.draw(boardBottomLetters[7-i], (i+0.5f) * ppuX,
                        0f * ppuY, 1f * ppuX, 0.5f * ppuY);
                spriteBatch.draw(boardLeftNumbers[7-i], 0.0f * ppuX,
                        (i + 0.5f) * ppuY, 0.5f * ppuX, 1f * ppuY);
            }

    }
	

	
	private void drawChecker() {

		for (Checker checker : board.getCheckers()) {
			if(!checker.getSelected()){
				if(checker.getColor()){
					spriteBatch.draw(checkerBlackTexture, checker.getPosition().x * ppuX, 
							checker.getPosition().y * ppuY, Checker.SIZE * ppuX, Checker.SIZE * ppuY);
				}	
				else spriteBatch.draw(checkerWhiteTexture, checker.getPosition().x * ppuX, 
							checker.getPosition().y * ppuY, Checker.SIZE * ppuX, Checker.SIZE * ppuY);
				}
			
			if(checker.getQueen()){
				if(checker.getColor()){
					spriteBatch.draw(checkerBlackQueenTexture, checker.getPosition().x * ppuX, 
							checker.getPosition().y * ppuY, Checker.SIZE * ppuX, Checker.SIZE * ppuY);
				}	
				else spriteBatch.draw(checkerWhiteQueenTexture, checker.getPosition().x * ppuX, 
							checker.getPosition().y * ppuY, Checker.SIZE * ppuX, Checker.SIZE * ppuY);
				}
			
			if(checker.getSelected()){
				if(checker.getColor()){
					spriteBatch.draw(checkerBlackSelTexture, checker.getPosition().x * ppuX, 
							checker.getPosition().y * ppuY, Checker.SIZE * ppuX, Checker.SIZE * ppuY);
				}	
				else spriteBatch.draw(checkerWhiteSelTexture, checker.getPosition().x * ppuX, 
							checker.getPosition().y * ppuY, Checker.SIZE * ppuX, Checker.SIZE * ppuY);
				}
			}
		}

	private void drawDebug() {
		// render cells
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.end();
		debugRenderer.begin(ShapeType.Line);
		for (Cell cell : board.getCells()) {
			Rectangle rect = cell.getBounds();
			float x1 = cell.getPosition().x + rect.x;
			float y1 = cell.getPosition().y + rect.y;	
			if(cell.getColor()){
				debugRenderer.setColor(new Color(1, 1, 1, 1));
			}
			else debugRenderer.setColor(new Color(0, 0, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}

	}
}
