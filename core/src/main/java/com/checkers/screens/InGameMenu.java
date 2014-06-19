package com.checkers.screens;

/**
 * Created by forrana on 14.06.14.
 */
        import CheckersClient.core.CheckersClient;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.InputProcessor;
        import com.badlogic.gdx.Screen;
        import com.checkers.network.client.NetworkClient;
        import com.checkers.view.InGameMenuStage;


public class InGameMenu implements Screen, InputProcessor {
    CheckersClient thisClient;

    public InGameMenu(CheckersClient client){

        thisClient = client;

    }

    //	public MainMenuRenderer   render;
    InGameMenuStage inGameMenuStage;
    private int width = 200;
    private int height = 200;
    @Override
    public void render(float delta) {

        inGameMenuStage.render(delta);

    }
    @Override
    public void resize(int width, int height) {
        inGameMenuStage.resize(width, height);
        this.width = width;
        this.height = height;

    }
    @Override
    public void show() {
        // TODO Auto-generated method stub
        inGameMenuStage = new InGameMenuStage(thisClient);
        inGameMenuStage.create();
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
        inGameMenuStage.dispose();
        Gdx.input.setInputProcessor(null);
    }

}

