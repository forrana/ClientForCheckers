package com.checkers.view;

/**
 * Created with IntelliJ IDEA.
 * User: forrana
 * Date: 16.08.13
 * Time: 22:41
 * To change this template use File | Settings | File Templates.
 */

import CheckersClient.core.CheckersClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.checkers.network.client.NetworkClient;
import com.checkers.support.fonts.FontGenerator;
import com.checkers.support.locale.Localization;

import java.util.Map;


public class LoginStage {
    private static final float CAMERA_WIDTH = 8.5f;
    private static final float CAMERA_HEIGHT = 8.5f;

    private Camera cam;

    CheckersClient thisClient;
    NetworkClient networkClient;
    public Stage stage;
    Skin skin;
    SpriteBatch batch;

    public LoginStage(CheckersClient client, NetworkClient iclient){
        thisClient = client;
        networkClient = iclient;
        create ();
    }

    public void create () {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        FontGenerator fontGenerator = new FontGenerator();
        BitmapFont font = fontGenerator.getFont();

        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("data/bwNine.png")), 12, 12, 12, 12);
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
        table.setWidth(Gdx.graphics.getWidth() / 2);
        table.setBackground(background);
        table.center().center();

        stage.addActor(table);
// Create a new TextButtonStyle

        Color redC = new Color(1f,0f,0f,1f);
        Color greenC = new Color(1f,0f,0f,1f);
        Color blueC = new Color(1f,0f,0f,1f);

        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), greenC,background);

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
        String[] labels;
        Localization localization = new Localization();
        Map<String, String> fields = Localization.getFields();
        System.out.println("fields:"+fields.get("Submit"));
        System.out.println("fields:"+fields.get("Registration"));
        System.out.println("locale:"+java.util.Locale.getDefault().toString());
        if(!java.util.Locale.getDefault().toString().equalsIgnoreCase("ru_RU")){
            String[] tmp = {"Submit","Registration","Login:","Password:"};
            labels = tmp;
        }else{
            String[] tmp = {"Подтвердить","Регистрация","Логин:","Пароль:"};
            labels =  tmp;
        }

        //final TextButton button = new TextButton("Submit", style);
        final TextButton button = new TextButton(fields.get("Submit"), style);
        button.center().center();
        button.setWidth(Gdx.graphics.getWidth() / 3);
        //final TextButton buttonReg = new TextButton("Registration", style);
        final TextButton buttonReg = new TextButton(fields.get("Registration"), style);

        buttonReg.center().center();
        buttonReg.setWidth(Gdx.graphics.getWidth() / 3);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.background = background;
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        //final Label loginL = new Label("Login:", labelStyle);
        final Label loginL = new Label(fields.get("Login:"), labelStyle);
        table.add(loginL);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = tEdit;

        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.LIGHT_GRAY;
        textFieldStyle.selection = tButton;

        final TextField loginT = new TextField("", textFieldStyle);
        loginT.setWidth(Gdx.graphics.getWidth()/3);
        table.add(loginT);

        table.row();

        //final Label passwordL = new Label("Password:", labelStyle);
        final Label passwordL = new Label(fields.get("Password:"), labelStyle);
        passwordL.setWidth(Gdx.graphics.getWidth()/3);
        table.add(passwordL);

        final TextField passwordT = new TextField("",textFieldStyle);
        passwordT.setPasswordMode(true);
        passwordT.setPasswordCharacter('*');

        table.add(passwordT);
        table.row();
        table.add();
        table.add(button);
        table.row();
        table.add();
        table.add(buttonReg);

        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if(!loginT.getText().isEmpty() || !passwordT.getText().isEmpty()) {
                    NetworkClient.auth(loginT.getText(), passwordT.getText());
                    try {
                        if (checkAuthorization()) {
                            System.out.println("AllOk");
                            System.out.println("user name:" + NetworkClient.userN);
                            thisClient.setScreen(thisClient.mMenu);
                        } else System.out.println("Wrong data!!!!");
                    }catch(Exception e){
                        System.out.println("err!"+e.getMessage());
                        e.printStackTrace();

                        System.out.println("Something wrong!");
                    }
                    }else System.out.println("Fill fields please!");

            }
        });
        buttonReg.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                thisClient.setScreen(thisClient.registration);
            }
        });

    }

    public boolean checkAuthorization(){

        Long req;

            networkClient = new NetworkClient();
            req = networkClient.getCurrUser();

            if(req != null){
                System.out.println("req:"+req);
            //    try {
              //  NetworkClient.auth();
                networkClient.getAllGames();
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
        Gdx.gl.glClearColor(0.0f, 0.5f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        Table.drawDebug(stage);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}