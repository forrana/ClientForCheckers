package com.checkers.network.client;

import com.checkers.server.beans.ListenObjects;

/**
 * Created with IntelliJ IDEA.
 * User: forrana
 * Date: 14.01.14
 * Time: 1:18
 * To change this template use File | Settings | File Templates.
 */
public class ListenObjectsHandler {
    private ListenObjects listenObjects;

    public ListenObjectsHandler(){
        listenObjects = new ListenObjects();
    }

    public void setListenObjects(ListenObjects inputObj){
        listenObjects = inputObj;
    }
    public ListenObjects getListenObjects(){
        return listenObjects;
    }

}
