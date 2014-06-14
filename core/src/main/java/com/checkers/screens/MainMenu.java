package com.checkers.screens;

import CheckersClient.core.CheckersClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.checkers.controllers.MainMenuController;
import com.checkers.network.client.NetworkClient;
import com.checkers.view.MainMenuStage;

public class MainMenu implements Screen, InputProcessor {
	MainMenuController controller; 
//	public MainMenuRenderer   render;
    MainMenuStage mStage;
    private int width;
    private int height;
    public  NetworkClient networkClient;

    public CheckersClient thisClient;

    public MainMenu(CheckersClient client, NetworkClient iclient){

        thisClient = client;

    }

	@Override
	public void render(float delta) {

        mStage.render(delta);

	}

	@Override
	public void resize(int width, int height) {
        //render.setSize(width, height);
        mStage.resize(width,height);
        this.width = width;
        this.height = height;

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
        mStage     = new MainMenuStage(thisClient);
        mStage.create();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int iX, int iY, int pointer, int button) {
		// TODO Auto-generated method stub
		controller.setCoord1(iX, iY);
		return false;
	}

	@Override
	public boolean touchUp(int iX, int iY, int pointer, int button) {
		// TODO Auto-generated method stub
		controller.setCoord2(iX, iY);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
        mStage.dispose();
        thisClient.dispose();
		Gdx.input.setInputProcessor(null);
	}

}
