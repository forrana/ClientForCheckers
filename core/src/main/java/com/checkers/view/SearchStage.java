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
import com.checkers.support.fonts.FontGenerator;
import com.checkers.support.locale.Localization;

import java.util.ArrayList;
import java.util.Map;


public class SearchStage {

    NetworkClient networkClient = new NetworkClient();
    CheckersClient thisClient = new CheckersClient();

    public SearchStage(CheckersClient inpClient, NetworkClient inetworkClient){
        thisClient = inpClient;
        create ();
    }

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
        FontGenerator fontGenerator = new FontGenerator(22f);
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


        List.ListStyle listStyle = new List.ListStyle();
        listStyle.selection = tButton;
        listStyle.font = font;
        listStyle.fontColorUnselected = Color.WHITE;
        listStyle.fontColorSelected = Color.LIGHT_GRAY;

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.background = tEdit;
        scrollPaneStyle.vScroll = tEdit;
        scrollPaneStyle.vScrollKnob = tEdit;
        scrollPaneStyle.corner = tEdit;

        Map<String, String> fields = Localization.getFields("SearchPage");

// Instantiate the Button itself.
//Main page
        final Label topLabel = new Label(fields.get("Search game:"), labelStyle);
        final Label gNameL = new Label(fields.get("Game name"), labelStyle);
        final TextField gNameT = new TextField(fields.get("name"), textFieldStyle);
        final Label gTypeL = new Label(fields.get("Game type"), labelStyle);
        final TextField gTypeT = new TextField(fields.get("type"), textFieldStyle);
        //**************Game Filter section
//Description
        final Label gBoardL = new Label(fields.get("Board type"), labelStyle);
        final TextField gBoardT = new TextField(fields.get("8x8"), textFieldStyle);
        final Label gCreaterL = new Label(fields.get("Creator"), labelStyle);
        final TextField gCreaterT = new TextField(fields.get("creator"), textFieldStyle);

        Table tableFilter1 = new Table();
        tableFilter1.setBackground(tButton);
        tableFilter1.add(gNameL);
        tableFilter1.add(gNameT);
        tableFilter1.add(gTypeL);
     //   gTypeT.setMaxLength(10);
        tableFilter1.add(gTypeT);
        Table tableFilter2 = new Table();
        tableFilter2.setBackground(tButton);
     //   tableFilter2.row();
        tableFilter2.add(gBoardL);
     //   gBoardT.setMaxLength(10);
        tableFilter2.add(gBoardT);
        tableFilter2.add(gCreaterL);
      //  gCreaterT.setMaxLength(10);
        tableFilter2.add(gCreaterT);

        table.add(tableFilter1);
        table.row();
        table.add(tableFilter2);
        table.row();
        Table tableGameList = new Table();
     //   tableGameList.setFillParent(true);
     //   tableGameList.setBackground(tButton);
  //      tableGameList.left().bottom();
        for(String tttGame:gamesList){
            System.out.println(tttGame);
        }
    //Game list
        final Label gameListL = new Label(fields.get("Game list"), labelStyle);
        final Label lGameDetail = new Label(fields.get("Game detail"), labelStyle);
        final Label lGameName = new Label(fields.get("Game name"), labelStyle);
        final Label lGameType = new Label(fields.get("Game type"), labelStyle);
        final Label lGameNameE = new Label(games.get(0).getName(), labelStyle);
        final Label lGameTypeE = new Label(games.get(0).getType(), labelStyle);
        final Label lBoardType = new Label(fields.get("Board type"), labelStyle);
        final Label lBoardTypeE = new Label(games.get(0).getBoard(), labelStyle);
        final Label lCreator = new Label(fields.get("Creator"), labelStyle);
        final Label lCreatorE = new Label(games.get(0).getWhite().getFirstName() + ' ' +
                games.get(0).getWhite().getLastName(), labelStyle);
        final Label lDescription = new Label(fields.get("Description"), labelStyle);
        final Label lDescriptionE = new Label(games.get(0).getDescription(), labelStyle);
        final TextButton buttonBack = new TextButton(fields.get("<- Back"), style);
        final TextButton buttonConnect = new TextButton(fields.get("CONNECT"), style);

        final List gameList;
        gameList = new List(listStyle);
        gameList.setItems(gamesList.toArray());
        ScrollPane gameListPane = new ScrollPane(gameList, scrollPaneStyle);

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

        tableGameDetail.add(lGameDetail);
       // tableGameDetail.setBackground(tButton);
        tableGameDetail.row();

        tableGameDetail.add(lGameName);
        tableGameDetail.add(lGameNameE);
        tableGameDetail.row();

        tableGameDetail.add(lGameType);
        tableGameDetail.add(lGameTypeE);
        tableGameDetail.row();

        tableGameDetail.add(lBoardType);
        tableGameDetail.add(lBoardTypeE);
        tableGameDetail.row();

        tableGameDetail.add(lCreator);
        tableGameDetail.add(lCreatorE);
        tableGameDetail.row();

        tableGameDetail.add(lDescription);
        tableGameDetail.add(lDescriptionE);
        tableGameDetail.row();

        buttonConnect.center().center();
        tableGameDetail.add();
        tableGameDetail.add(buttonConnect);

        table.add(tableGameDetail);


        Table table1 = new Table();
        table1.setFillParent(true);
        table1.setBackground(background);
        table1.center().center();
        Table tabName = new Table();
        tabName.add(gNameL);
        tabName.add(gNameT);

        Table tabGameT = new Table();
        tabGameT.add(gTypeL);
        tabGameT.add(gTypeT);

        Table tabBoardT = new Table();
        tabBoardT.add(gBoardL);
        tabBoardT.add(gBoardT);

        Table tabCreater = new Table();
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
