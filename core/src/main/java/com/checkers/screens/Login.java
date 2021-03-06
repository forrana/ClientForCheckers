package com.checkers.screens;

import CheckersClient.core.CheckersClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.checkers.network.client.NetworkClient;
import com.checkers.view.LoginStage;

public class Login implements Screen, InputProcessor {
    public CheckersClient thisClient;
    public NetworkClient networkClient;

    public Login(CheckersClient client, NetworkClient inetworkClient){

        networkClient = inetworkClient;
        thisClient = client;

    }

    //	public MainMenuRenderer   render;
    LoginStage loginStage;
    private int width = 200;
    private int height = 200;
    @Override
    public void render(float delta) {

        loginStage.render(delta);

    }
    @Override
    public void resize(int width, int height) {
        //render.setSize(width, height);
        loginStage.resize(width, height);
        this.width = width;
        this.height = height;

    }
    @Override
    public void show() {
        // TODO Auto-generated method stub
        loginStage = new LoginStage(thisClient, networkClient);
        loginStage.create();
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

        return false;
    }
    @Override
    public boolean touchUp(int iX, int iY, int pointer, int button) {
        // TODO Auto-generated method stub

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
        loginStage.dispose();
        thisClient.dispose();
        Gdx.input.setInputProcessor(null);
    }

}

