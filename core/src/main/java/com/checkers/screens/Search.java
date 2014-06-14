package com.checkers.screens;

import CheckersClient.core.CheckersClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.checkers.network.client.NetworkClient;
import com.checkers.view.SearchStage;

/**
 * Created with IntelliJ IDEA.
 * User: forrana
 * Date: 17.08.13
 * Time: 6:12
 * To change this template use File | Settings | File Templates.
 */

public class Search implements Screen, InputProcessor {
    //	public MainMenuRenderer   render;
    SearchStage searchStage;
    private int width;
    private int height;
    public CheckersClient thisClient;
    NetworkClient networkClient;
//    public NetworkClient networkClient = new NetworkClient();

    public Search(CheckersClient client, NetworkClient inetworkClient){
        networkClient = inetworkClient;
        thisClient = client;

    }

    @Override
    public void render(float delta) {

        searchStage.render(delta);

    }
    @Override
    public void resize(int width, int height) {
        //render.setSize(width, height);
        searchStage.resize(width, height);
        this.width = width;
        this.height = height;

    }
    @Override
    public void show() {
        // TODO Auto-generated method stub
        searchStage = new SearchStage(thisClient, networkClient);
        searchStage.create();
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
        searchStage.dispose();
        thisClient.dispose();
        Gdx.input.setInputProcessor(null);
    }

}

