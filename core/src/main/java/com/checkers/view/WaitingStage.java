package com.checkers.view;

/**
 * Created by forrana on 07.06.14.
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
        import com.checkers.network.client.GameListener;
        import com.checkers.network.client.NetworkClient;

        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

public class WaitingStage {
    private static final float CAMERA_WIDTH = 8.5f;
    private static final float CAMERA_HEIGHT = 8.5f;

    private Camera cam;

    CheckersClient thisClient;
    NetworkClient networkClient;
    public Stage stage;
    Skin skin;
    SpriteBatch batch;
    String endMessage;
    //vars for open game thread
    private GameListener gameListener;
    private boolean isExec = true;
    private boolean isCreated = false;


    public WaitingStage(NetworkClient currentNetworkClient, CheckersClient client){
        networkClient = currentNetworkClient;
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
        final TextButton exitButton = new TextButton("Exit", style);
        exitButton.center().center();
        final TextButton mainMenuButton = new TextButton("Main menu", style);
        mainMenuButton.center().center();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.background = background;
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;


        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = tEdit;

        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.LIGHT_GRAY;
        textFieldStyle.selection = tButton;

        final Label endMessage = new Label("Searching opponent...", labelStyle);
        table.add(endMessage);
        table.row();
        table.row();
        table.add(mainMenuButton);
        table.row();
        table.add(exitButton);

        exitButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
            }
        });
        mainMenuButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                thisClient.setScreen(thisClient.mMenu);
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
        //Table.drawDebug(stage);
        if(isExec){
            gameListener = new GameListener(CheckersClient.networkClient);
            ExecutorService exec = Executors.newSingleThreadExecutor();
            exec.execute(gameListener);
            isExec = !isExec;
        }
        if(gameListener.isCanceled()){
            // try {
            if(gameListener.getGame().getListenObjects().getGame().getState().equalsIgnoreCase("game")){
                System.out.println("New game stage change screen");
                NetworkClient.gameH.game = gameListener.getGame().getListenObjects().getGame();
                thisClient.setScreen(thisClient.game);
            }else System.out.println("Game stage:"+gameListener.getGame().getListenObjects().getGame().getState());
            //network.listenGame().getListenObjects().getStep();
            isExec = !isExec;
            //  } catch (IOException e) {
            //    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            //  }
        }

    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
