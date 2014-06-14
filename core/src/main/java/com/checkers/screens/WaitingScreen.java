package com.checkers.screens;

/**
 * Created by forrana on 07.06.14.
 */
        import CheckersClient.core.CheckersClient;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.InputProcessor;
        import com.badlogic.gdx.Screen;
        import com.checkers.network.client.NetworkClient;
        import com.checkers.view.EndGameStage;
        import com.checkers.view.LoginStage;
        import com.checkers.view.WaitingStage;


public class WaitingScreen implements Screen, InputProcessor {
    NetworkClient networkClient;
    CheckersClient thisClient;

    public WaitingScreen(NetworkClient currentNetworkClient, CheckersClient client){

        thisClient = client;
        networkClient = currentNetworkClient;

    }

    //	public MainMenuRenderer   render;
    WaitingStage waitingStage;
    private int width = 200;
    private int height = 200;
    @Override
    public void render(float delta) {

        waitingStage.render(delta);

    }
    @Override
    public void resize(int width, int height) {
        waitingStage.resize(width, height);
        this.width = width;
        this.height = height;

    }
    @Override
    public void show() {
        // TODO Auto-generated method stub
        waitingStage = new WaitingStage(networkClient, thisClient);
        waitingStage.create();
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
        waitingStage.dispose();
        Gdx.input.setInputProcessor(null);
    }

}


