package com.checkers.network.client;

/**
 * Created with IntelliJ IDEA.
 * User: forrana
 * Date: 14.01.14
 * Time: 1:31
 * To change this template use File | Settings | File Templates.
 */
public class GameThread {

    GameListener gameListener;

    public GameThread(GameListener gListener){
        gameListener = gListener;
    }

}
