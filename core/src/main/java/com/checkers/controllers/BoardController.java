package com.checkers.controllers;

import CheckersClient.core.CheckersClient;
import com.badlogic.gdx.math.Vector2;
import com.checkers.model.Board;
import com.checkers.model.Cell;
import com.checkers.model.Checker;
import com.checkers.network.client.GameListener;
import com.checkers.network.client.NetworkClient;
import com.checkers.network.client.SocketListener;
import com.checkers.server.beans.Step;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BoardController {
	
	private MoveValidator validator;
	public  NetworkClient network;
    public  CheckersClient thisClient;

    private Cell startCell;
    private Cell targetCell;
    private static Checker selectedChecker;
    private static String  currResult;

    private SocketListener sListener;
	private GameListener gameListener;
    private boolean isMove = false;
	private boolean init = true;
	private int		time = 0;
	private long	Suid = 0;
	private boolean isExec = true;
	private Board board;

	public BoardController(Board inpBoard, MoveValidator inpValidator, CheckersClient inputClient){
		this.validator = inpValidator;
        thisClient = inputClient;
        this.board = inpBoard;
        network = new NetworkClient();
        validator.setNetwork(network);

        try{
            for(Step tmpStep : network.getAllSteps()){
                parseStep(tmpStep);
            }
        }
        catch(Exception e){
            System.out.println("Ходов нет");
        }
	}
	
	public void setIsMove(boolean b) {
		isMove = b;	
	}
	
	public void setCoord1(float iX, float iY){
      //This making for enforce from crash in case no checker choose.
      try{
        selectedChecker = board.getCheckerByDeskCoord(iX, iY);

        //Here we compare color selected checker with player color
        //And if fighting in progress check fighting checker with selected
        System.out.println(selectedChecker.getColor() + " : " + MoveValidator.isPlayerWhite);
        System.out.println("GL pos:"+selectedChecker.getPosition());
        System.out.println(MoveValidator.getFightinChecker() == null);

        if(selectedChecker.getColor() != MoveValidator.isPlayerWhite &&
          (MoveValidator.getFightinChecker() == null ||
           MoveValidator.getFightinChecker().getIndex() == selectedChecker.getIndex())
          ){
		    validator.setSelectedChecker(iX, iY);
            startCell = board.getCellByDeskCoord(iX, iY);
        }
      }
      catch(Exception e){}
	}
	
	public void setCoord2(float iX, float iY){
        //Here we compare color selected checker with player color
        //And if fighting in progress check fighting checker with selected
        try{
        if(selectedChecker.getColor() != MoveValidator.isPlayerWhite &&
           (MoveValidator.getFightinChecker() == null ||
                   (MoveValidator.getFightinChecker().getIndex() == selectedChecker.getIndex()))
          ){
		    validator.setTargetCell(iX, iY);
            targetCell = board.getCellByDeskCoord(iX, iY);
        }
        }catch(Exception e){

        }
	}
	
	public void setNetCoord1(float iX, float iY){
        validator.setNetSelectedChecker(iX, iY);
        startCell = board.getCellByGLCoord(iX, iY);
        selectedChecker = board.getCheckerByGLCoord(iX, iY);
    }
	
	public void setNetCoord2(float iX, float iY){
        validator.setNetTargetCell(iX, iY);
        targetCell = board.getCellByGLCoord(iX, iY);

        String result;
        result = validator.doCheckMove();
        System.out.println("result:"+result);
        switch (Integer.parseInt(result)){
            case 2: virtualStep(result);break;
            case 3: virtualStep(result);break;
            case 4: virtualStep(result);break;
            case 5: virtualStepExt(result);break;
            case 6: virtualStepExt(result);break;
            case 7: virtualStep(result);break;
            default: validator.returnChecker();break;
        }
        validator.selectedChecker = null;
        validator.clearCells();
        clearKilled();
        //selectedChecker.setPosition(targetCell.getPosition());
        ///selectedChecker = null;

        //validator.isBlackTurn = !validator.isBlackTurn;
        //if(validator.isBlackTurn)System.out.println("Black turn");
        //else  System.out.println("White turn");
    }



    private boolean parseStep(Step iStep){
            System.out.println("Virtual step:"+iStep.getStep());
       try{
            StringTokenizer stok = new StringTokenizer(iStep.getStep(), "-:");
            if(stok.hasMoreTokens()){
                String lastMove = new String();
                String nextTok  = new String();
                Vector2 vec = new Vector2();

                nextTok = stok.nextToken();
                vec = validator.strToMove(nextTok);
                setNetCoord1(vec.x, vec.y);

                nextTok = stok.nextToken();
                vec = validator.strToMove(nextTok);
                setNetCoord2(vec.x,vec.y);
                lastMove = nextTok;

                while(stok.hasMoreTokens()){
                    vec = validator.strToMove(lastMove);
                    setNetCoord1(vec.x, vec.y);
                    nextTok = stok.nextToken();
                    vec = validator.strToMove(nextTok);
                    setNetCoord2(vec.x,vec.y);
                    lastMove = nextTok;
                }
            }
       }
       catch(Exception e){
           System.out.println("Smth wrang in parsing step!:"+e.getMessage());
           e.printStackTrace();
           return false;
       }

       return true;
    }

    private String moveToStr(float x, float y){
        if(!MoveValidator.isPlayerWhite){
            x = 8.0f - x;
            y = 8.0f - y;
        }
        String currStep = new String();
        if(currStep.length() != 0)currStep += '-';
        currStep += (char)((int)(x - board.getBoardBottom()) + (int)'a');
        currStep += Integer.toString((int)(y+ board.getBoardBottom()));

        return currStep;
    }

    private boolean realStep(String result){
            System.out.println("one cell step:"+result);
            currResult = "";
            currResult += moveToStr(startCell.getPosition().x, startCell.getPosition().y);
            currResult += "-";
            currResult += moveToStr(targetCell.getPosition().x, targetCell.getPosition().y);
            boolean serverIsAllowed = network.sendStepInStr(currResult, validator.isBlackTurn);

            System.out.println(serverIsAllowed);
            if(serverIsAllowed){
                selectedChecker.setPosition(targetCell.getPosition());
                validator.checkQueen();
            }
                else{
                    selectedChecker.setPosition(startCell.getPosition());
                    return false;
                }
            validator.isBlackTurn = !validator.isBlackTurn;
            if(validator.isBlackTurn)System.out.println("Black turn");
                else  System.out.println("White turn");

        return true;
    }

    private boolean realSingleKill(String result){
        System.out.println("Single kill:"+result);
        currResult = "";
        currResult += moveToStr(startCell.getPosition().x, startCell.getPosition().y);
        currResult += ":";
        currResult += moveToStr(targetCell.getPosition().x, targetCell.getPosition().y);
        boolean serverIsAllowed = network.sendStepInStr(currResult, validator.isBlackTurn);

        System.out.println(serverIsAllowed);
        if(serverIsAllowed)selectedChecker.setPosition(targetCell.getPosition());
        else{
            selectedChecker.setPosition(startCell.getPosition());
            unmarkKilled();
            return false;
        }

        validator.isBlackTurn = !validator.isBlackTurn;
        //validator.isCanFight = true;
        if(validator.isBlackTurn)System.out.println("Black turn");
            else  System.out.println("White turn");

        return true;
    }

    private boolean firstStepFromMultiKill(String result){
        System.out.println("First step from multi kill:" + currResult);
        currResult = "";
        currResult += moveToStr(startCell.getPosition().x, startCell.getPosition().y);
        currResult += ":";
        currResult += moveToStr(targetCell.getPosition().x, targetCell.getPosition().y);
        currResult += ":";

        return  true;
    }
    private boolean nextStepFromMultiKill(String result){
        System.out.println("Next step from multi kill:" + currResult);
        currResult += moveToStr(targetCell.getPosition().x, targetCell.getPosition().y);
        currResult += ":";

        return  true;
    }
    private boolean lastStepFromMultiKill(String result){
        System.out.println("Last step from multi kill:" + currResult);
        currResult += moveToStr(targetCell.getPosition().x, targetCell.getPosition().y);
        System.out.println("Send to server:" + currResult);

        boolean serverIsAllowed = network.sendStepInStr(currResult, validator.isBlackTurn);

        System.out.println(serverIsAllowed);
        if(serverIsAllowed)selectedChecker.setPosition(targetCell.getPosition());
        else{
            selectedChecker.setPosition(startCell.getPosition());
            unmarkKilled();
            return false;
        }

        validator.isBlackTurn = !validator.isBlackTurn;
        if(validator.isBlackTurn)System.out.println("Black turn");
        else  System.out.println("White turn");

        return  true;
    }

    private boolean virtualStep(String result){
        System.out.println("Controller virtual step");
        selectedChecker.setPosition(targetCell.getPosition());
        validator.isBlackTurn = !validator.isBlackTurn;
        if(validator.isBlackTurn)System.out.println("Black turn");
            else  System.out.println("White turn");
        clearKilled();

        return true;
    }

    private boolean virtualStepExt(String result){
        System.out.println("Controller virtual step");
        selectedChecker.setPosition(targetCell.getPosition());
        clearKilled();

        return true;
    }

    private boolean clearKilled(){
        int i = 0;
        for(Checker tmpChecker : board.getCheckers())
            if(tmpChecker.getIsKilled()){
                board.delChecker(tmpChecker);
                i++;
                System.out.println("marked to delete:"+i);
            }

        return true;
    }

    private boolean unmarkKilled(){
        int i = 0;
        for(Checker tmpChecker : board.getCheckers())
            if(tmpChecker.getIsKilled()){
                tmpChecker.clearIsKilled();
                i++;
                System.out.println("marked to live:"+i);
            }

        return true;
    }


        /** The main update method **/
	public void update(float delta) {
          //if(false){
		if(validator.isPlayerWhite == validator.isBlackTurn){
			if(isExec){
                    gameListener = new GameListener(network);
					ExecutorService exec = Executors.newSingleThreadExecutor();
                    exec.execute(gameListener);
					isExec = !isExec;
            }
			if(gameListener.isCanceled()){
                try {
                    System.out.println("Game state:"+NetworkClient.gameH.game.getState());
                    if(NetworkClient.gameH.game.getState().toUpperCase().contains("CLOSE")){
                        System.out.println("Game is closed:"+NetworkClient.gameH.game.getResolution());
                        thisClient.setScreen(thisClient.endGame);
                    }
                    else if(network.listenGame().getListenObjects().getStep() != null)
                        parseStep(network.listenGame().getListenObjects().getStep());
                } catch (IOException e) {
                    System.out.println("BCSPERR:"+e.getMessage());
                }
                isExec = !isExec;
			}
		}

		if(isMove){
            String result;
            result = validator.doCheckMove();
            System.out.println("result:"+result);
            switch (Integer.parseInt(result)){
                case 2: virtualStep(result);break;
                case 3: realStep(result);break;
                case 4: realSingleKill(result);break;
                case 5: firstStepFromMultiKill(result);break;
                case 6: nextStepFromMultiKill(result);break;
                case 7: lastStepFromMultiKill(result);break;
                default: validator.returnChecker();break;
            }
			validator.selectedChecker = null;
			setIsMove(false);
			validator.clearCells();
            clearKilled();
        }
	}
}
