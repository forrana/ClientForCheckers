package com.checkers.network.client;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: forrana
 * Date: 13.01.14
 * Time: 0:58
 * To change this template use File | Settings | File Templates.
 */
public class GameListener implements Runnable{

    NetworkClient network;
    protected boolean isCancel = false;
    ListenObjectsHandler objectsHandler = new ListenObjectsHandler();
    /**
     *
     * @param net - current NetworkClient
     */
    public GameListener(NetworkClient net){
        network = net;
    }
    /**
     *
     * @return Cancel status flag.
     * Is work not Canceled - return false.
     */
    public boolean isCanceled(){
        return isCancel;
    }

    public void setIsCanceled(){
        isCancel = !isCancel;
    }
    /**
     * Check isCancel flag and return step.
     * @return if thread work cancelled and false, if not.
     */
    public ListenObjectsHandler getGame(){
        if(isCancel)
            return objectsHandler;
        else return null;
    }

    @Override
    public void run() {
        do{
            try {
                objectsHandler = network.listenGame();
            } catch (IOException e) {
                System.out.println("Game listener error:"+e.getMessage());  //To change body of catch statement use File | Settings | File Templates.
            }
        }while( objectsHandler == null);
        network.objectsHandler = objectsHandler;
        try{
        if(objectsHandler.getListenObjects().getGame() != null){
           NetworkClient.gameH.game = objectsHandler.getListenObjects().getGame();
        }
        }catch(Exception e){}
        //NetworkClient.gameH.listenObjectsHandler
        isCancel =  true;
    }



}
