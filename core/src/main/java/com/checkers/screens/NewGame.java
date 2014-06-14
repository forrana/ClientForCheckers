package com.checkers.screens;

import CheckersClient.core.CheckersClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.checkers.network.client.NetworkClient;
import com.checkers.view.NewGameStage;

/**
 * Created with IntelliJ IDEA.
 * User: forrana
 * Date: 17.08.13
 * Time: 6:04
 * To change this template use File | Settings | File Templates.
 */
public class NewGame implements Screen, InputProcessor {
    //	public MainMenuRenderer   render;
    NewGameStage newGameStage;
    private int width;
    private int height;
    public CheckersClient thisClient;
    public NetworkClient networkClient;

    public NewGame(CheckersClient client, NetworkClient inetworkClient){

        networkClient = inetworkClient;
        thisClient = client;

    }

    @Override
    public void render(float delta) {

        newGameStage.render(delta);

    }
    @Override
    public void resize(int width, int height) {
        //render.setSize(width, height);
        newGameStage.resize(width, height);
        this.width = width;
        this.height = height;

    }
    @Override
    public void show() {
        // TODO Auto-generated method stub
        newGameStage = new NewGameStage(networkClient, thisClient);
        newGameStage.create();

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
        newGameStage.dispose();
        thisClient.dispose();
        Gdx.input.setInputProcessor(null);
    }

}



