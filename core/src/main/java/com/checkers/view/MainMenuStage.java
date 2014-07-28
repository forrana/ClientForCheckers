package com.checkers.view;

import CheckersClient.core.CheckersClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.checkers.support.fonts.FontGenerator;


/**
 * Created with IntelliJ IDEA.
 * User: forrana
 * Date: 21.07.13
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
public class MainMenuStage {
    public Stage stage;
    Skin skin;
    SpriteBatch batch;
    List list;
    CheckersClient thisClient;

    private static TextureRegion cellWhiteTexture;

    public MainMenuStage(CheckersClient client){
        thisClient = client;
        create ();
    }

    public void create () {

        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("data/bwNine.png")), 12, 12, 12, 12);
        //NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("assets/data/selecter.png")), 12, 12, 12, 12);
        NinePatchDrawable draw = new NinePatchDrawable(patch);

        NinePatch patchSelect = new NinePatch(new Texture(Gdx.files.internal("data/aluminium.png")), 12, 12, 12, 12);
        NinePatchDrawable selecter = new NinePatchDrawable(patchSelect);

        NinePatch patchBackground = new NinePatch(new Texture(Gdx.files.internal("data/background_calligraphy.png")));
        NinePatchDrawable background = new NinePatchDrawable(patchBackground);

        NinePatch patchTEdit = new NinePatch(new Texture(Gdx.files.internal("data/tEdit_calligraphy.png")));
        NinePatchDrawable tEdit = new NinePatchDrawable(patchTEdit);

        NinePatch patchTButton = new NinePatch(new Texture(Gdx.files.internal("data/button_calligraphy.png")));
        NinePatchDrawable tButton = new NinePatchDrawable(patchTButton);

        Table table = new Table();
        table.setFillParent(true);
        table.center().center();
        table.setBackground(background);
        stage.addActor(table);

        FontGenerator fontGenerator = new FontGenerator(26);
        BitmapFont font = fontGenerator.getFont();

        // Create a new TextButtonStyle
        Color redC = new Color(1f,0f,0f,1f);
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
        final TextButton newGame = new TextButton("New game", style);
        final TextButton retGame = new TextButton("Return in game", style);
        final TextButton connGame = new TextButton("Connect to game", style);
        final TextButton exitGame = new TextButton("Exit", style);
        final TextButton logOut = new TextButton("Log out", style);

        table.add(newGame);
        table.row();
        table.add(retGame);
        table.row();
        table.add(connGame);
        table.row();
        table.add(logOut);
        table.row();
        table.add(exitGame);
//**********************Listeners
        connGame.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {

                thisClient.setScreen(thisClient.search);

            }
        });
        logOut.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {

                thisClient.setScreen(thisClient.login);

            }
        });
        retGame.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {

                thisClient.setScreen(thisClient.search);

            }
        });
        newGame.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {

                thisClient.setScreen(thisClient.nGame);

            }
        });
        exitGame.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {

                dispose();

            }
        });
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
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