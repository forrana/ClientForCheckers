package com.checkers.network.client;

import com.checkers.server.beans.ExceptionMessage;
import com.checkers.server.beans.Game;
import com.checkers.server.beans.Step;
import com.checkers.server.beans.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StepHandler {

	public Step step = new Step();
	Date date = new Date();
	
	public void stepHandler(){
		
	}
	
	public void initStep(Game Gauid, User Uuid){

		/*step.setCreated(date);
		step.setGame(Gauid);
		step.setStep(null);
		step.setSuid(null);
		step.setUser(Uuid);*/
	}
	
	public void setStep(String move){
		
		step.setStep(move);
		
	}
	
	public long getStepSuid(){	
		if(step.getSuid() != null){	
			return step.getSuid();
		}
		else return 0l;
	}

    public List<Step> getAllSteps() throws IOException {
        //defenition
        DefaultHttpClient httpclient = NetworkClient.getHttpClient();
        BasicHttpContext localcontext = NetworkClient.getLocalContext();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Step> steps = new ArrayList<Step>();
        //end

        HttpGet request = new HttpGet(NetworkClient.currLink + "games/"+NetworkClient.gameH.game.getGauid()+"/steps");
        HttpResponse response = httpclient.execute(request,localcontext);
        HttpEntity entity = response.getEntity();

        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = new String("");

        line = rd.readLine();

        while (line != null) {

            StringReader reader = new StringReader(line);
            try{
                for(Step tmpStep : mapper.readValue(reader, Step[].class))
                    steps.add(tmpStep);
            }catch(Exception e){
                reader = new StringReader(line);
                ExceptionMessage eMsg = mapper.readValue(reader, ExceptionMessage.class);
                System.out.println("error:"+ eMsg.getCode() +"->" + eMsg.getMessage());
                return null;
            }

            line = rd.readLine();
        }

        EntityUtils.consume(entity);
        return steps;

    }



	//this method get current step
	public Step getLastDataNow() throws ClientProtocolException, IOException{
	//defenition	
        DefaultHttpClient httpclient = NetworkClient.getHttpClient();
        BasicHttpContext localcontext = NetworkClient.getLocalContext();
		ObjectMapper mapper = new ObjectMapper();
		Step step;
    //end    	

		HttpGet request = new HttpGet(NetworkClient.currLink + "games/"+NetworkClient.gameH.game.getGauid()+"/steps?mode=opponentStepAsync");
		
		HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = new String("");
        
        if ((line = rd.readLine()) != null) {
        	StringReader reader = new StringReader(line);
              try{
        	    step = mapper.readValue(reader, Step.class);
        	    return step;
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

	//this method get current game 
			public Step getDataNow(long Suid) throws ClientProtocolException, IOException{
			//defenition	
                DefaultHttpClient httpclient = NetworkClient.getHttpClient();
                BasicHttpContext  localcontext = NetworkClient.getLocalContext();

				ObjectMapper mapper = new ObjectMapper();
				Step step;
		    //end
				HttpGet request = new HttpGet(NetworkClient.currLink + "steps/" + Long.toString(Suid));
		        HttpResponse response = httpclient.execute(request, localcontext);
		        HttpEntity entity = response.getEntity();
		        
		        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		        String line = new String("");
		        
		        if ((line = rd.readLine()) != null) {
		        	StringReader reader = new StringReader(line);

		        try{
                    step = mapper.readValue(reader, Step.class);
		         	return step;
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
			
			public Step postDataNow(Step currStep) throws ClientProtocolException, IOException{

                DefaultHttpClient httpclient = NetworkClient.getHttpClient();
                BasicHttpContext  localcontext = NetworkClient.getLocalContext();

				StringWriter writer = new StringWriter();
				ObjectMapper mapper = new ObjectMapper();

				mapper.writeValue(writer, currStep);
				Step step = new Step();

		        HttpPost post = new HttpPost(NetworkClient.currLink + "games/"+NetworkClient.gameH.game.getGauid()+"/steps");
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
                        step = mapper.readValue(reader, Step.class);
		        		return step;
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
