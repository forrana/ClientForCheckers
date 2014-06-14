package com.checkers.screens;


import CheckersClient.core.CheckersClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.checkers.controllers.BoardController;
import com.checkers.controllers.MoveValidator;
import com.checkers.model.Board;
import com.checkers.network.client.NetworkClient;
import com.checkers.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor{

	private Board 			board;
	private WorldRenderer 	renderer;	
	private BoardController	controller;
	private MoveValidator	validator;
    NetworkClient networkClient;

	private int width;
	private int height;

    public CheckersClient thisClient;

    public GameScreen(CheckersClient client, NetworkClient inetworkClient){
     //   NetworkClient networkClient = inetworkClient;
        thisClient = client;
    }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		controller.update(delta);
		renderer.render();
	}
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
	    this.width = width;
	    this.height = height;
	}

	@Override
	public void show() {
		board = new Board();
		renderer = new WorldRenderer(board, true);
		validator = new MoveValidator(board);
		controller = new BoardController(board, validator, thisClient);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}
//Control event's
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(validator.isBlackTurn != validator.isPlayer0){
			controller.setCoord1(screenX, screenY);
			renderer.selChecker(screenX, screenY);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(validator.isBlackTurn != validator.isPlayer0){
			controller.setCoord2(screenX, screenY);
			renderer.selChecker(screenX, screenY);
			controller.setIsMove(true);
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		validator.moveChecker(screenX, screenY);
		
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		
		return false;
	}

}
