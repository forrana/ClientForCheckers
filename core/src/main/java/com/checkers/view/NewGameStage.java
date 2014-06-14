package com.checkers.view;

import CheckersClient.core.CheckersClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.checkers.network.client.GameListener;
import com.checkers.network.client.NetworkClient;
import com.checkers.server.beans.Game;
//import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: forrana
 * Date: 17.08.13
 * Time: 6:05
 * To change this template use File | Settings | File Templates.
 */
public class NewGameStage {
    NetworkClient networkClient = new NetworkClient();
    public Stage stage;
    Skin skin;
    SpriteBatch batch;
    CheckersClient thisClient;


    public NewGameStage(NetworkClient inetworkClient,CheckersClient inputClient){
       // networkClient = inetworkClient;
        thisClient = inputClient;
        create ();
    }

    public void create () {

        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Widget widget = new Widget();

        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("data/bwNine.png")), 12, 12, 12, 12);
        NinePatchDrawable draw = new NinePatchDrawable(patch);

        NinePatch patchBackground = new NinePatch(new Texture(Gdx.files.internal("data/background_calligraphy.png")));
        NinePatchDrawable background = new NinePatchDrawable(patchBackground);

        NinePatch patchTEdit = new NinePatch(new Texture(Gdx.files.internal("data/tEdit_calligraphy.png")));
        NinePatchDrawable tEdit = new NinePatchDrawable(patchTEdit);

        NinePatch patchTButton = new NinePatch(new Texture(Gdx.files.internal("data/button_calligraphy.png")));
        NinePatchDrawable tButton = new NinePatchDrawable(patchTButton);

        Table table = new Table();
        table.setFillParent(true);
        table.center().center();
        //table.size(200,200);

        table.setBackground(background);

//****************Styles

        Color redC = new Color(1f,0f,0f,1f);
        Color greenC = new Color(1f,0f,0f,1f);
        Color blueC = new Color(1f,0f,0f,1f);

        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), greenC,draw);

        TextButton.TextButtonStyle style;
        style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.checkedFontColor = Color.BLACK;
        style.checkedOverFontColor = Color.BLACK;
        style.downFontColor = Color.GRAY;
        style.overFontColor = Color.DARK_GRAY;
        style.fontColor = Color.BLACK;
        style.up = tButton;


// **************************Elements
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.background = background;
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.BLACK;

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = tEdit;
        textFieldStyle.font = new BitmapFont();
        textFieldStyle.fontColor = Color.LIGHT_GRAY;
        textFieldStyle.selection = tButton;


        final Label lGameName = new Label("Game name", labelStyle);
        final TextField tGameName = new TextField("", textFieldStyle);

        final Label lGameType = new Label("Game type", labelStyle);
        final TextField tGameType = new TextField("", textFieldStyle);

        final Label lBoardType = new Label("Board type", labelStyle);
        final TextField tBoardType = new TextField("", textFieldStyle);

        final Label lGameDescription = new Label("Game description", labelStyle);
        final TextField tGameDescription = new TextField("", textFieldStyle);

        final TextButton bCreate = new TextButton("Create game", style);
        final TextButton bBack = new TextButton("<- Back", style);

  //*******************Filing table
        table.add();
        table.add(bBack);
        table.row();
        table.add(lGameName);
        table.add(tGameName);
        table.row();
        table.add(lGameType);
        table.add(tGameType);
        table.row();
        table.add(lBoardType);
        table.add(tBoardType);
        table.row();
        table.add(lGameDescription);
        table.add(tGameDescription);
        table.row();
        table.add();
        table.add(bCreate);

        stage.addActor(table);
//*********************Events
    /*    boolean b = bCreate.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Clicked! Is checked: " + bCreate.isChecked());
                try {
                    networkClient.createGame(tGameName.getText(), tGameType.getText(),
                                             tBoardType.getText(), tGameDescription.getText());
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                //button.setText(passwordT.getText());
                /*
                NetworkClient.auth(loginT.getText(), passwordT.getText());
                if(checkAuthorization())System.out.println("AllOk");
                else System.out.println("Wrong data!!!!");
                */
     //       }
     //   });

        boolean b = bCreate.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    Game tmp =  networkClient.createGame(tGameName.getText(), tGameType.getText(),
                            tBoardType.getText(), tGameDescription.getText());
                    System.out.println("GUID:"+tmp.getGauid());
                    NetworkClient.gameH.game = networkClient.getGameByGAUID(tmp.getGauid());
                    NetworkClient.gameH.initCreatedGame();
                    isCreated = true;
                    CheckersClient.networkClient = networkClient;
                    thisClient.setScreen(thisClient.waitingScreen);
                } catch (IOException e) {
                    System.out.println("Stage error:"+e.getMessage());  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        boolean bBackResult = bBack.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                thisClient.setScreen(thisClient.mMenu);
            }
        });
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    private GameListener gameListener;
    private boolean isExec = true;
    private boolean isCreated = false;

    public void render (float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        /*
        if(isCreated){
           if(isExec){
            gameListener = new GameListener(networkClient);
            ExecutorService exec = Executors.newSingleThreadExecutor();
            exec.execute(gameListener);
            isExec = !isExec;
           }
            if(gameListener.isCanceled()){

                    if(gameListener.getGame().getListenObjects().getGame().getState().equalsIgnoreCase("game")){
                        System.out.println("New game stage change screen");
                        NetworkClient.gameH.game = gameListener.getGame().getListenObjects().getGame();
                        thisClient.setScreen(thisClient.game);
                    }else System.out.println("Game stage:"+gameListener.getGame().getListenObjects().getGame().getState());

                    isExec = !isExec;

            }
        }
        */
    }

    public void dispose() {
          stage.dispose();
          skin.dispose();
    }
}