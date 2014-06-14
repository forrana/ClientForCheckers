
package com.checkers.network.client;

import com.checkers.server.beans.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/*
 * Author Alexey Kuchin
 * 
 * This class get and return game objects from server
 * 
 */
public class GameHandler{
    public Game game = new Game();
    public ListenObjectsHandler listenObjectsHandler = new ListenObjectsHandler();
    public User userBlack = new User();
    public User userWhite = new User();
    private Date date = new Date();

	public GameHandler(String inName, String inType, String inBoard, String inDescript){
            game.setName(inName);
        if(inType.length() >= 1){game.setType(inType);}
            else game.setType(null);
        if(inBoard.length() >= 1){game.setBoard(inBoard);}
            else game.setBoard(null);
        if(inDescript.length() >= 1){game.setDescription(inDescript);}
            else game.setDescription(null);

	}
	
    public List<Game> getAllGames() throws IOException {
        //defenition
        DefaultHttpClient httpclient = NetworkClient.getHttpClient();
        BasicHttpContext localcontext = NetworkClient.getLocalContext();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Game> game = new ArrayList<Game>();
        //end

        HttpGet request = new HttpGet(NetworkClient.currLink + "games/");
        HttpResponse response = httpclient.execute(request,localcontext);
        HttpEntity entity = response.getEntity();

        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = new String("");

        line = rd.readLine();

        while (line != null) {

            StringReader reader = new StringReader(line);
        try{
            for(Game tmpGame : mapper.readValue(reader, Game[].class))
                game.add(tmpGame);
        }catch(Exception e){
            reader = new StringReader(line);
            ExceptionMessage eMsg = mapper.readValue(reader, ExceptionMessage.class);
            System.out.println("error:"+ eMsg.getCode() +"->" + eMsg.getMessage());
            return null;
        }

            line = rd.readLine();
        }

        EntityUtils.consume(entity);
        return game;

    }
	
	//this method get current game 
	public Game getDataNow(long Gauid) throws ClientProtocolException, IOException{
	//defenition	
        DefaultHttpClient httpclient = NetworkClient.getHttpClient();
        BasicHttpContext localcontext = NetworkClient.getLocalContext();
		ObjectMapper mapper = new ObjectMapper();
		Game game;
    //end    

		HttpGet request = new HttpGet(NetworkClient.currLink + "games/" + Long.toString(Gauid));
        HttpResponse response = httpclient.execute(request,localcontext);
        HttpEntity entity = response.getEntity();
        
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = new String("");
        
        if ((line = rd.readLine()) != null) {
        	StringReader reader = new StringReader(line);
        try{
        	game = mapper.readValue(reader, Game.class);
        	return game;
        }catch(Exception e){
            reader = new StringReader(line);
            ExceptionMessage eMsg = mapper.readValue(reader, ExceptionMessage.class);
            System.out.println("error:"+ eMsg.getCode() +"->" + eMsg.getMessage());
            return null;
        }
        	
        }
        EntityUtils.consume(entity);
        return null;
	}
    public void initCreatedGame(){

           listenObjectsHandler.getListenObjects().setGame(NetworkClient.gameH.game);

    }
    public ListenObjectsHandler listenGame() throws IOException {

            long GAUID = NetworkClient.gameH.game.getGauid();
            long SUID;
        try{
           SUID = NetworkClient.lastStep.getSuid();
        }catch(Exception e){
           SUID = Integer.MAX_VALUE;
        }

        //if(SUID == null) SUID = Integer.MAX_VALUE;
       // SUID = Integer.MAX_VALUE;

        String state = NetworkClient.gameH.game.getState();
        System.out.println("GAUID:"+GAUID+";SUID:"+SUID+";state:"+state);

        DefaultHttpClient httpclient = NetworkClient.getHttpClient();
        BasicHttpContext localcontext = NetworkClient.getLocalContext();

        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();

        //mapper.writeValue(writer, game);

        HttpGet get = new HttpGet(NetworkClient.currLink + "games/" + GAUID+"?mode=listen&lastStepSuid="+SUID+"&currentGameState="+state);
        /*/games/{gauid}?mode=listen&lastStepSuid={suid}&currentGameState={state}
           */
       // StringEntity input = new StringEntity(writer.toString()); //here instead of JSON you can also have XML
       // input.setContentType("application/json");
        //get.set .setEntity(input);
        HttpResponse response = httpclient.execute(get, localcontext);
        HttpEntity entity = response.getEntity();

        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = new String("");

        if ((line = rd.readLine()) != null) {
            System.out.println("Game handler:"+line);
            StringReader reader = new StringReader(line);
            try{
                ListenObjectsHandler value = new ListenObjectsHandler();
                value.setListenObjects(mapper.readValue(reader, ListenObjects.class));
                //game.setGauid(value.getGauid());
                //System.out.println("Game handler all ok:"+value.getListenObjects().getGame().getState());
                return value;
            }catch(Exception e){
               // Step value = new Step();
               try{
                 NetworkClient.lastStep  = mapper.readValue(reader, Step.class);
               }catch(Exception e1){
                    System.out.println("Game handler exception:" + e1.getMessage());
                    reader = new StringReader(line);
                    ExceptionMessage eMsg = mapper.readValue(reader, ExceptionMessage.class);
                    System.out.println("error:"+ eMsg.getCode() +"->" + eMsg.getMessage());
                    return null;
               }
            }
        }
        System.out.println("Game  hendler exit with null");
        EntityUtils.consume(entity);
        return null;
    }

    /*
    /games/{gauid}?mode=listen&lastStepSuid={suid}&currentGameState={state}
    */

	public Game joinToGame(long GAUID) throws ClientProtocolException, IOException{

        DefaultHttpClient httpclient = NetworkClient.getHttpClient();
        BasicHttpContext localcontext = NetworkClient.getLocalContext();

        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(writer, game);

        HttpPut put = new HttpPut(NetworkClient.currLink + "games/" + GAUID+"?action=join");


        StringEntity input = new StringEntity(writer.toString()); //here instead of JSON you can also have XML
        input.setContentType("application/json");
        put.setEntity(input);
        HttpResponse response = httpclient.execute(put, localcontext);
        HttpEntity entity = response.getEntity();

        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = new String("");

        if ((line = rd.readLine()) != null) {
            StringReader reader = new StringReader(line);
            Game value = mapper.readValue(reader, Game.class);
        try{
            game.setGauid(value.getGauid());
            return value;
        }catch(Exception e){
            reader = new StringReader(line);
            ExceptionMessage eMsg = mapper.readValue(reader, ExceptionMessage.class);
            System.out.println("error:"+ eMsg.getCode() +"->" + eMsg.getMessage());
            return null;
        }
        }
        EntityUtils.consume(entity);
        return null;

    }

	public Game postDataNow() throws ClientProtocolException, IOException{

        DefaultHttpClient httpclient = NetworkClient.getHttpClient();
        BasicHttpContext localcontext = NetworkClient.getLocalContext();

		StringWriter writer = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();

        System.out.println("game_param='"+ game.getType()+"'"+" '"+game.getBoard()+"'");

        mapper.writeValue(writer, game);

        HttpPost post = new HttpPost(NetworkClient.currLink + "games/");
        StringEntity input = new StringEntity(writer.toString()); //here instead of JSON you can also have XML
        input.setContentType("application/json");
        post.setEntity(input);
        HttpResponse response = httpclient.execute(post, localcontext);
        HttpEntity entity = response.getEntity();

        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		String line = new String("");
		
        if ((line = rd.readLine()) != null) {
        	 StringReader reader = new StringReader(line);
        try{
            Game value = mapper.readValue(reader, Game.class);
            game.setGauid(value.getGauid());
        	 return value;
        }catch(Exception e){
            reader = new StringReader(line);
            ExceptionMessage eMsg = mapper.readValue(reader, ExceptionMessage.class);
            System.out.println("error:"+ eMsg.getCode() +"->" + eMsg.getMessage());
            return null;
        }
        }
        EntityUtils.consume(entity);
        return null;
	}	
}
	

