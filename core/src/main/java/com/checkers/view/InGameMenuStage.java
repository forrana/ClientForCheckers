package com.checkers.view;

/**
 * Created by forrana on 14.06.14.
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
        import com.badlogic.gdx.utils.viewport.ScreenViewport;
        import com.badlogic.gdx.utils.viewport.StretchViewport;
        import com.checkers.network.client.NetworkClient;
        import com.checkers.support.fonts.FontGenerator;
        import com.checkers.support.locale.Localization;

        import java.util.Map;


public class InGameMenuStage {

    private static final float CAMERA_WIDTH = 8.5f;
    private static final float CAMERA_HEIGHT = 8.5f;

    private Camera cam;

    CheckersClient thisClient;
    NetworkClient networkClient;
    public Stage stage;
    Skin skin;
    SpriteBatch batch;
    String endMessage;

    public InGameMenuStage(CheckersClient client){
        thisClient = client;
        create ();
    }

    public void create () {

        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

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
        table.setBackground(background);
        table.center().center();

        stage.addActor(table);
// Create a new TextButtonStyle

        Color redC = new Color(1f,0f,0f,1f);
        Color greenC = new Color(1f,0f,0f,1f);
        Color blueC = new Color(1f,0f,0f,1f);

        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), greenC,background);

        FontGenerator fontGenerator = new FontGenerator();
        BitmapFont font = fontGenerator.getFont();

        TextButton.TextButtonStyle style;
        style = new TextButton.TextButtonStyle();
        style.font = font;
        style.checkedFontColor = Color.BLACK;
        style.checkedOverFontColor = Color.BLACK;
        style.downFontColor = Color.GRAY;
        style.overFontColor = Color.DARK_GRAY;
        style.fontColor = Color.BLACK;
        style.up = tButton;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.background = background;
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = tEdit;

        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.LIGHT_GRAY;
        textFieldStyle.selection = tButton;
// Instantiate the Button itself.
        Map<String, String> fields = Localization.getFields("InGameMenuPage");

        final TextButton exitButton = new TextButton(fields.get("Exit"), style);
        //exitButton.center().center();
        final TextButton mainMenuButton = new TextButton(fields.get("Main menu"), style);
        //mainMenuButton.center().center();
        final TextButton returnButton = new TextButton(fields.get("Return"), style);
        returnButton.center().center();

        table.add(returnButton);
        table.row();
        table.add(mainMenuButton);
        table.row();
        table.add(exitButton);

        exitButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        mainMenuButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                NetworkClient.gameH.game = null;
                NetworkClient.lastStep = null;
                thisClient.setScreen(thisClient.mMenu);
            }
        });
        returnButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                //NetworkClient.gameH.game = null;
                //NetworkClient.lastStep = null;
                thisClient.setScreen(thisClient.game);
            }
        });

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
//        skin.dispose();
    }
}