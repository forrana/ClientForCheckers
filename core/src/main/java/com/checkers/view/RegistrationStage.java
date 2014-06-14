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
import com.checkers.network.client.NetworkClient;
/*
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
import com.checkers.network.client.NetworkClient;
import CheckersClient.core.*;

import java.io.IOException;
 */

/**
 * Created with IntelliJ IDEA.
 * User: forrana
 * Date: 16.08.13
 * Time: 22:41
 * To change this template use File | Settings | File Templates.
 */


public class RegistrationStage {
    CheckersClient thisClient;
    NetworkClient networkClient;
    public Stage stage;
    Skin skin;
    SpriteBatch batch;

    public RegistrationStage(CheckersClient client, NetworkClient iclient){
        thisClient = client;
        networkClient = iclient;
        create ();
    }

    public void create () {

        batch = new SpriteBatch();
        //stage = new Stage(100, 100, false);
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("data/bwNine.png")), 12, 12, 12, 12);
        // NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("assets/data/selecter.png")), 12, 12, 12, 12);
        NinePatchDrawable draw = new NinePatchDrawable(patch);

        NinePatch patchSelect = new NinePatch(new Texture(Gdx.files.internal("data/selecter.png")), 12, 12, 12, 12);
        NinePatchDrawable selecter = new NinePatchDrawable(patchSelect);

        NinePatch patchBackground = new NinePatch(new Texture(Gdx.files.internal("data/background_calligraphy.png")));
        NinePatchDrawable background = new NinePatchDrawable(patchBackground);

        NinePatch patchTEdit = new NinePatch(new Texture(Gdx.files.internal("data/tEdit_calligraphy.png")));
        NinePatchDrawable tEdit = new NinePatchDrawable(patchTEdit);

        NinePatch patchTButton = new NinePatch(new Texture(Gdx.files.internal("data/button_calligraphy.png")));
        NinePatchDrawable tButton = new NinePatchDrawable(patchTButton);

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(background);

        table.center().center();
// Create a new TextButtonStyle

        Color redC = new Color(1f,0f,0f,1f);
        Color greenC = new Color(1f,0f,0f,1f);
        Color blueC = new Color(1f,0f,0f,1f);

        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), greenC,background);

        BitmapFont font = new BitmapFont();
        font.scale(1.3f);

        TextButton.TextButtonStyle style;
        style = new TextButton.TextButtonStyle();
        style.font = font;
        style.checkedFontColor = Color.BLACK;
        style.checkedOverFontColor = Color.BLACK;
        style.downFontColor = Color.GRAY;
        style.overFontColor = Color.DARK_GRAY;
        style.fontColor = Color.BLACK;
        style.up = tButton;

// Instantiate the Button itself.
        final TextButton button = new TextButton("   Submit    ", style);
        button.center().center();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.background = background;
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        final Label loginL = new Label("Login:", labelStyle);
        table.add(loginL);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = tEdit;

        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.LIGHT_GRAY;
        textFieldStyle.selection = tButton;

        final TextField loginT = new TextField("", textFieldStyle);
        table.add(loginT);
        table.row();

        final Label emailL = new Label("Enter email:", labelStyle);
        table.add(emailL);
        final TextField emailT = new TextField("", textFieldStyle);
        table.add(emailT);
        table.row();

        final Label passwordL = new Label("Enter pass:", labelStyle);
        table.add(passwordL);

        final TextField passwordT = new TextField("",textFieldStyle);
        passwordT.setPasswordMode(true);
        passwordT.setPasswordCharacter('*');

        table.add(passwordT);
        table.row();
        final Label passwordL1 = new Label("Re enter pass:", labelStyle);
        table.add(passwordL1);

        final TextField passwordT1 = new TextField("",textFieldStyle);
        passwordT1.setPasswordMode(true);
        passwordT1.setPasswordCharacter('*');
        table.add(passwordT1);
        table.row();

        table.add();
        table.add(button);

        stage.addActor(table);

        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if(!passwordT.getText().isEmpty() && passwordT.getText().equals(passwordT1.getText())){
                if(!loginT.getText().isEmpty() && !passwordT.getText().isEmpty() && !emailT.getText().isEmpty()){
            //        NetworkClient.auth(loginT.getText(), passwordT.getText());
                    NetworkClient.createNewUser(loginT.getText(),passwordT.getText(), emailT.getText());
                }else System.out.println("Fill fields please!");
                }else{
                    System.out.println("Pass no match!");
                    passwordT.setText("");
                    passwordT1.setText("");
                }
            }
        });
    }

    public boolean checkAuthorization(){

        Long req;

        networkClient = new NetworkClient();
        req = networkClient.getCurrUser();

        if(req != null){
            System.out.println(req);
            //    try {
            //  NetworkClient.auth();
            //networkClient.getAllGames();
            //    } catch (IOException e) {
            //      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            // }
            return true;
        }
        return false;
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}