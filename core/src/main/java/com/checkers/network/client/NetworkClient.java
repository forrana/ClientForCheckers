package com.checkers.network.client;

import com.checkers.server.beans.Game;
import com.checkers.server.beans.Step;
import com.checkers.server.beans.User;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

//import org.apache.http.client.protocol.ClientContext;
//import org.apache.http.client.protocol.ClientContext;

public class NetworkClient {
	/**
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 **/
	public static String userN;
	private static String pass;
    private static DefaultHttpClient httpclient;
    private static BasicHttpContext  localcontext;
    public static  GameHandler gameH = new GameHandler("","","","");
    public static  UserHandler userH = new UserHandler();
    public static  Step lastStep = new Step();

    public static final String currLink = "http://localhost:8080/checkers-0.4-SNAPSHOT/";
    private static final String currHost = "localhost";
    private static final Short currPort = 8080;
    //public static final String currLink = "http://api.shashki.in.ua/";
    //UserHandler userH = new UserHandler();
	SocketListener sListener = new SocketListener(this);
	SocketThread sThread = new SocketThread(sListener);
	public StepHandler stepH = new StepHandler();
    ListenObjectsHandler objectsHandler = new ListenObjectsHandler();

    public static DefaultHttpClient getHttpClient(){

        return httpclient;
    }

    public static BasicHttpContext getLocalContext(){

        return localcontext;

    }

	public SocketListener getSListener(){
		return sListener;
	}

    public static void auth(String userName, String iPass){

        userN = userName;
        pass  = iPass;

        httpclient = new DefaultHttpClient();
        HttpHost targetHost = new HttpHost(currHost, currPort,"http");
        //HttpHost targetHost = new HttpHost("api.shashki.in.ua", 80,"http");
        httpclient.getCredentialsProvider().setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials(NetworkClient.userN, NetworkClient.pass));
        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local
        // auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);
        // Add AuthCache to the execution context
        localcontext = new BasicHttpContext();
        localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);
        //localcontext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

    }
    public NetworkClient(){


    }
    //Users methods
    public Long getCurrUser(){
       try {

            User user =  userH.getCurrUser();
            userH.curUser = user;
            return user.getUuid();

        } catch (IOException e) {
            //  e.printStackTrace();
            System.out.println("Login incorrect!!!");
            return null;
        }

    }
    public static Long createNewUser(String userName, String userPass, String email){
        try {

            User user = new User();
            user.setLogin(userName);
            user.setPassword(userPass);
            user.setEmail(email);
            userH.createNewUser(user, currHost, currPort);
            return user.getUuid();

        }catch (IOException e) {
            //  e.printStackTrace();
            System.out.println("Create failed!");
            return null;
        }

    }
    //game methods
    public Game createGame(String inName, String inType, String inBoard, String inDescript) throws IOException {

        gameH = new GameHandler(inName, inType, inBoard, inDescript);
        try {
  //          System.out.println("GUID:"+gameH.game.getGauid());
            Game tmp = (Game)gameH.postDataNow();

            return tmp;
  //          System.out.println("GUID:"+gameH.game.getGauid());
        } catch (IOException e) {
            System.out.println("Network err:"+e.getMessage());
        }
        return null;
    }
    public ArrayList<Step> getAllSteps(){

        ArrayList<Step> oList =  new ArrayList<Step>();

        try {
            oList = (ArrayList)stepH.getAllSteps();
            System.out.println("Returned: "+ oList.size() + " games");

        } catch (IOException e) {
            System.out.println("Sorrt, empty");  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
        return oList;
    }
    public ArrayList<Game> getAllGames(){
        ArrayList<Game>  oList =  new ArrayList<Game>();
        try {
            oList = (ArrayList)gameH.getAllGames();
        System.out.println("Returned: "+ oList.size() + " games");
        } catch (IOException e) {
           System.out.println("Sorrt, empty");  //To change body of catch statement use File | Settings | File Templates.
        }
        return oList;
    }

    public Game getGameByGAUID(long GAUID){
        ArrayList<Game>  oList =  new ArrayList<Game>();
        try {
            System.out.println("Game found");
            return gameH.getDataNow(GAUID);
        } catch (IOException e) {
            System.out.println("Game not found");
        }
        return null;
    }
    /*
    /games/{gauid}
    */
    public Game joinToGame(long GAUID){

        try {
            gameH.game = gameH.joinToGame(GAUID);
            return gameH.game;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
   //     return null;
    }
    public ListenObjectsHandler listenGame() throws IOException {

      //  try{
            objectsHandler = gameH.listenGame();
            System.out.println("network module Ok!");
       // }catch(Exception e){
            //ListenObjectsHandler listenObjectsHandler = new ListenObjectsHandler();
       //     System.out.println("network module err:"+e.getMessage()+e.getStackTrace().toString());
       // }

        return objectsHandler;
    }

    //step methods
    public Step getLastStep(){
        try{
            lastStep = stepH.getLastDataNow();
            return lastStep;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
	
	public long getStepSuid(){
		return stepH.getStepSuid();
		//else return 0;
	}
	
	public void setStepSuid(Step step){
		stepH.step.setSuid(step.getSuid());
	}
	
	
	public void initStep(boolean isBlack){
	//	if(isBlack)stepH.initStep(gameH.game, gameH.userWhite);
	//	else stepH.initStep(gameH.game, gameH.userBlack);
		
	}
	
	public Step getStep(long Suid){
		try {
			return stepH.getDataNow(Suid);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isWhite(){
		if(stepH.step.getUser().getUuid() == gameH.game.getWhite().getUuid())return true;
			else return false;
	}

	public boolean sendStepInStr(String moveStr, boolean isBlack){
 		stepH.setStep(moveStr);
		try {
            NetworkClient.auth(userN,pass);
			if((lastStep = stepH.postDataNow(stepH.step)) != null){
                System.out.println("Step:"+stepH.step.getStep());
                System.out.println("User:"+NetworkClient.userN);
                System.out.println("last step:"+lastStep.getSuid());
                //NetworkClient.gameH.game.
                return true;
            }
            return false;
        } catch (ClientProtocolException e) {
			e.printStackTrace();
            System.out.println("Server disallowed this turn!");
            return false;
		} catch (IOException e) {
            System.out.println("Something wrong!");
            e.printStackTrace();
            return false;
		}
	}
	
	public void sendStep(float ix, float iy, float ix1, float iy1, boolean isBlack){
		Date date = new Date();
		String str = new String();
		stepH.date = date;
		str = Float.toString(ix) + "-" + Float.toString(iy) + "-" + Float.toString(ix1) + "-" +  Float.toString(iy1);
		initStep(isBlack);
		stepH.setStep(str);
		try {
			stepH.step.setSuid(stepH.postDataNow(stepH.step).getSuid());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
