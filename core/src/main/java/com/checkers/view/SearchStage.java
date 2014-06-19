package com.checkers.view;

/**
 * Created with IntelliJ IDEA.
 * User: forrana
 * Date: 18.08.13
 * Time: 21:00
 * To change this template use File | Settings | File Templates.
 */

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
import com.checkers.server.beans.Game;

import java.util.ArrayList;


public class SearchStage {
    NetworkClient networkClient = new NetworkClient();
    CheckersClient thisClient = new CheckersClient();

    public SearchStage(CheckersClient inpClient, NetworkClient inetworkClient){
  //      networkClient = inetworkClient;
        thisClient = inpClient;
        create ();
    }

    private String[] listEntries = new String[]{"This is a list entry", "And another one", "The meaning of life", "Is hard to come by"
           };
    private ArrayList<String> gamesList = new ArrayList<String>();

    public Stage stage;
    Skin skin;
    SpriteBatch batch;


    public void create () {

        final ArrayList<Game> games =  networkClient.getAllGames();

        System.out.println(games.size());

        gamesList.clear();

        for(Game tmpGame: games){

            gamesList.add(tmpGame.getName());

        }


        batch = new SpriteBatch();
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("data/bwNine.png")), 12, 12, 12, 12);
        // NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("assets/data/selecter.png")), 12, 12, 12, 12);
        NinePatchDrawable draw = new NinePatchDrawable(patch);

        //NinePatch patchBack = new NinePatch(new Texture(Gdx.files.internal("assets/data/aluminium.png")), 12, 12, 12, 12);
      //  NinePatchDrawable background = new NinePatchDrawable(patchBack);

        NinePatch patchSelect = new NinePatch(new Texture(Gdx.files.internal("data/aluminium.png")), 12, 12, 12, 12);
        NinePatchDrawable selecter = new NinePatchDrawable(patchSelect);

        NinePatch patchBackground = new NinePatch(new Texture(Gdx.files.internal("data/background_calligraphy.png")));
        NinePatchDrawable background = new NinePatchDrawable(patchBackground);

        NinePatch patchTEdit = new NinePatch(new Texture(Gdx.files.internal("data/tEdit_calligraphy.png")));
        NinePatchDrawable tEdit = new NinePatchDrawable(patchTEdit);

        NinePatch patchTButton = new NinePatch(new Texture(Gdx.files.internal("data/button_calligraphy.png")));
        NinePatchDrawable tButton = new NinePatchDrawable(patchTButton);

        NinePatch patchTList = new NinePatch(new Texture(Gdx.files.internal("data/tList_calligraphy.png")));
        NinePatchDrawable tList = new NinePatchDrawable(patchTList);

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(background);

      //  table.center().top();
// Create a new TextButtonStyle

        Color redC = new Color(1f,0f,0f,1f);
        Color greenC = new Color(1f,0f,0f,1f);
        Color blueC = new Color(1f,0f,0f,1f);

        TextButton.TextButtonStyle style;
        style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.checkedFontColor = Color.BLACK;
        style.checkedOverFontColor = Color.BLACK;
        style.downFontColor = Color.GRAY;
        style.overFontColor = Color.DARK_GRAY;
        style.fontColor = Color.BLACK;
        style.up = tButton;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.background = background;
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.BLACK;


        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = tEdit;
        textFieldStyle.font = new BitmapFont();
        textFieldStyle.fontColor = Color.LIGHT_GRAY;
        textFieldStyle.selection = tButton;


        List.ListStyle listStyle = new List.ListStyle();
        listStyle.selection = tButton;
        listStyle.font = new BitmapFont();
        listStyle.fontColorUnselected = Color.WHITE;
        listStyle.fontColorSelected = Color.LIGHT_GRAY;

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.background = tEdit;
        scrollPaneStyle.vScroll = tEdit;
        scrollPaneStyle.vScrollKnob = tEdit;
        scrollPaneStyle.corner = tEdit;

// Instantiate the Button itself.
        final Label topLabel = new Label("Search game:", labelStyle);

        //**************Game Filter section

        Table tableFilter1 = new Table();
      //  tableFilter.setFillParent(true);
     //   tableFilter.set
        tableFilter1.setBackground(tButton);
     //   tableFilter.left().top();

      //  final Label filtrLabel = new Label("Filter:", labelStyle);
      //  tableFilter1.add(filtrLabel);
       // tableFilter1.row();

        final Label gNameL = new Label("Game name", labelStyle);
        tableFilter1.add(gNameL);

        final TextField gNameT = new TextField("name", textFieldStyle);
     //   gNameT.setMaxLength(10);
        tableFilter1.add(gNameT);

        final Label gTypeL = new Label("Game type", labelStyle);
        tableFilter1.add(gTypeL);

        final TextField gTypeT = new TextField("type", textFieldStyle);
     //   gTypeT.setMaxLength(10);
        tableFilter1.add(gTypeT);

        Table tableFilter2 = new Table();
        tableFilter2.setBackground(tButton);

     //   tableFilter2.row();

        final Label gBoardL = new Label("Board type", labelStyle);
        tableFilter2.add(gBoardL);

        final TextField gBoardT = new TextField("8x8", textFieldStyle);
     //   gBoardT.setMaxLength(10);
        tableFilter2.add(gBoardT);

        final Label gCreaterL = new Label("Creater", labelStyle);
        tableFilter2.add(gCreaterL);

        final TextField gCreaterT = new TextField("creater", textFieldStyle);
      //  gCreaterT.setMaxLength(10);

        tableFilter2.add(gCreaterT);

        table.add(tableFilter1);
        table.row();
        table.add(tableFilter2);
        table.row();

        //**************Game list section
        Table tableGameList = new Table();
     //   tableGameList.setFillParent(true);
     //   tableGameList.setBackground(tButton);
  //      tableGameList.left().bottom();

        //List gameList = new List(listEntries, listStyle);
        for(String tttGame:gamesList){

            System.out.println(tttGame);
        }

        System.out.println("size"+gamesList.size());

        final List gameList;

        gameList = new List(listStyle);

        gameList.setItems(gamesList.toArray());


        ScrollPane gameListPane = new ScrollPane(gameList, scrollPaneStyle);
        final Label gameListL = new Label("Game list", labelStyle);
        tableGameList.add(gameListL);
        tableGameList.row();
        tableGameList.add(gameListPane);
        tableGameList.columnDefaults(3);

        table.add(tableGameList);
        //**************Game Detail section
        Table tableGameDetail = new Table();
      //  tableGameDetail.setFillParent(true);
      //  tableGameDetail.setBackground(tButton);
        tableGameDetail.right().bottom();

        final Label lGameDetail = new Label("Game detail", labelStyle);
        tableGameDetail.add(lGameDetail);
       // tableGameDetail.setBackground(tButton);
        tableGameDetail.row();

        final Label lGameName = new Label("Game name", labelStyle);
        tableGameDetail.add(lGameName);
        final Label lGameNameE = new Label(games.get(0).getName(), labelStyle);
        tableGameDetail.add(lGameNameE);
        tableGameDetail.row();

        final Label lGameType = new Label("Game type", labelStyle);
        tableGameDetail.add(lGameType);
        final Label lGameTypeE = new Label(games.get(0).getType(), labelStyle);
        tableGameDetail.add(lGameTypeE);
        tableGameDetail.row();

        final Label lBoardType = new Label("Board type", labelStyle);
        tableGameDetail.add(lBoardType);
        final Label lBoardTypeE = new Label(games.get(0).getBoard(), labelStyle);
        tableGameDetail.add(lBoardTypeE);
        tableGameDetail.row();

        final Label lCreator = new Label("Creator", labelStyle);
        tableGameDetail.add(lCreator);
        final Label lCreatorE = new Label(games.get(0).getWhite().getFirstName() + ' ' +
                                          games.get(0).getWhite().getLastName(), labelStyle);
        tableGameDetail.add(lCreatorE);
        tableGameDetail.row();

        final Label lDescription = new Label("Description", labelStyle);
        tableGameDetail.add(lDescription);
        final Label lDescriptionE = new Label(games.get(0).getDescription(), labelStyle);
        tableGameDetail.add(lDescriptionE);
        tableGameDetail.row();

        final TextButton buttonBack = new TextButton("<- Back", style);
        final TextButton buttonConnect = new TextButton("*******CONNECT********", style);
        buttonConnect.center().center();

        tableGameDetail.add();
        tableGameDetail.add(buttonConnect);

        table.add(tableGameDetail);


        Table table1 = new Table();
        table1.setFillParent(true);
        table1.setBackground(background);
        table1.center().center();
        Table tabName = new Table();
        //tabName.setBackground(tButton);

        tabName.add(gNameL);
        tabName.add(gNameT);

        Table tabGameT = new Table();
       // tabGameT.setBackground(tButton);

        tabGameT.add(gTypeL);
        tabGameT.add(gTypeT);

        Table tabBoardT = new Table();
       // tabBoardT.setBackground(tButton);

        tabBoardT.add(gBoardL);
        tabBoardT.add(gBoardT);

        Table tabCreater = new Table();
        //tabCreater.setBackground(tButton);

        tabCreater.add(gCreaterL);
        tabCreater.add(gCreaterT);

        table1.add(buttonBack);
        table1.add(topLabel);
        table1.row();
        table1.add(tabName);
        table1.add(tabGameT);
        table1.row();
        table1.add(tabBoardT);
        table1.add(tabCreater);
        table1.row();
        table1.add(tableGameList);
        table1.add(tableGameDetail);
        //stage.addActor(tableFilter);
        //stage.addActor(tableGameList);

        stage.addActor(table1);

        buttonBack.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {

                thisClient.setScreen(thisClient.mMenu);

            }
        });

        boolean b = buttonConnect.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Clicked! Is checked: " + buttonConnect.isChecked());
                int index = gameList.getSelectedIndex();

               if( games.get(index).getWhite() != null &&
                       games.get(index).getWhite().getUuid() ==
                               NetworkClient.userH.curUser.getUuid()){
                   NetworkClient.gameH.game = games.get(index);
                   System.out.println("White");
               }else
               if( games.get(index).getBlack() != null &&
                       games.get(index).getBlack().getUuid() ==
                               NetworkClient.userH.curUser.getUuid()){
                   NetworkClient.gameH.game = games.get(index);
                System.out.println("Black");

            }else {
                   networkClient.joinToGame(games.get(index).getGauid());
                   System.out.println("Join");
                  // System.out.println("Black");
               }

                thisClient.setScreen(thisClient.game);
               /* NetworkClient.auth(loginT.getText(), passwordT.getText());

                if(checkAuthorization()){
                    System.out.println("AllOk");
                }
                else System.out.println("Wrong data!!!!");
               */
            }
        });

        boolean b1 = gameList.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

                int index = gameList.getSelectedIndex();
                System.out.println("Index"+index);
                System.out.println("Name" + gameList.getSelection());

                lGameNameE.setText(games.get(index).getName());
                lGameTypeE.setText(games.get(index).getType());
                lBoardTypeE.setText(games.get(index).getBoard());
                lDescriptionE.setText(games.get(index).getDescription());
            }
        });
    }

    public boolean checkAuthorization(){
        Long req;
        networkClient = new NetworkClient();
        req = networkClient.getCurrUser();

        if(req != null){
            System.out.println(req);
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
