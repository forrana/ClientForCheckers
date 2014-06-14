package com.checkers.network.client;

import com.checkers.server.beans.ExceptionMessage;
import com.checkers.server.beans.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
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
import java.util.Date;

//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;


public class UserHandler {

	public User userBlack = new User();
	public User userWhite = new User();
	public User curUser   = new User();
	Date date = new Date();
	
	public UserHandler(){
	
		
	}
	
	public void initUser(){

		
	}
	
	//this method get current user
        public User getCurrUser() throws IOException {
            DefaultHttpClient httpclient = NetworkClient.getHttpClient();
            BasicHttpContext  localcontext = NetworkClient.getLocalContext();

            ObjectMapper mapper = new ObjectMapper();
            HttpResponse response;
            BufferedReader rd;
            HttpEntity entity;
            User user;

            HttpGet request = new HttpGet(NetworkClient.currLink + "users/me" );
            response = httpclient.execute(request,localcontext);

            System.out.println(response.getStatusLine());

            entity = response.getEntity();

            rd = new BufferedReader (new InputStreamReader(entity.getContent()));

            String line = new String();

            if ((line = rd.readLine()) != null) {
                StringReader reader = new StringReader(line);
            try{

                user = mapper.readValue(reader, User.class);
                return user;
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

    //this method get current user
    public User createNewUser(User user, String currHost, Short currPort) throws ClientProtocolException, IOException{

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpHost targetHost = new HttpHost(currHost, currPort,"http");


        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, user);
        System.out.println("writer:"+writer.toString());
        System.out.println("login:"+user.getLogin() + ";pass:"+user.getPassword());

        HttpPost post = new HttpPost(NetworkClient.currLink + "users/registration/");
        //StringEntity input = new StringEntity(writer.toString()); //here instead of JSON you can also have XML
        StringEntity input = new StringEntity(
                        "{\"login\":"+"\""+user.getLogin()+"\","
                        +"\"email\":"+"\""+user.getEmail()+"\","
                        +"\"password\":"+"\""+user.getPassword()+"\"}"
        ); //here instead of JSON you can also have XML
        input.setContentType("application/json");
        System.out.println("input:"+input);
        post.setEntity(input);
        HttpResponse response = httpclient.execute(post);
        HttpEntity entity = response.getEntity();

        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = new String(" ");

        while ((line = rd.readLine()) != null) {
            StringReader reader = new StringReader(line);
            try{
                user = mapper.readValue(reader, User.class);
                return user;
            }catch(Exception e){
                reader = new StringReader(line);
                ExceptionMessage eMsg = mapper.readValue(reader, ExceptionMessage.class);
                System.out.println("error:"+ eMsg.getCode() +"->" + eMsg.getMessage());
                return null;
            }
        }
        EntityUtils.consume(entity);
        System.out.println("return null");
        return null;
    }

    public User getDataNow(long Uuid) throws ClientProtocolException, IOException{
		//defenition	
            DefaultHttpClient httpclient = NetworkClient.getHttpClient();
            BasicHttpContext  localcontext = NetworkClient.getLocalContext();

			ObjectMapper mapper = new ObjectMapper();
			User user;

			HttpGet request = new HttpGet(NetworkClient.currLink +"users/" + Long.toString(Uuid));
	        HttpResponse response = httpclient.execute(request, localcontext);
	        HttpEntity entity = response.getEntity();
	        
	        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
	        String line;
	        
	        if((line = rd.readLine()) != null) {
                StringReader reader = new StringReader(line);
	        try{
	         	user = mapper.readValue(reader, User.class);
	        	return user;
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
		
		public User postDataNow(User user) throws ClientProtocolException, IOException{

            DefaultHttpClient httpclient = NetworkClient.getHttpClient();
            BasicHttpContext  localcontext = NetworkClient.getLocalContext();

            StringWriter writer = new StringWriter();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(writer, user);

	        HttpPost post = new HttpPost(NetworkClient.currLink + "users/");
	        StringEntity input = new StringEntity(writer.toString()); //here instead of JSON you can also have XML
	        input.setContentType("application/json");
	        post.setEntity(input);
	        HttpResponse response = httpclient.execute(post);
	        HttpEntity entity = response.getEntity();
	        
	        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));      
			String line = new String(" ");
			
	        while ((line = rd.readLine()) != null) {
                StringReader reader = new StringReader(line);
	        try{
                user = mapper.readValue(reader, User.class);
	        	 return user;
            }catch(Exception e){
                reader = new StringReader(line);
                ExceptionMessage eMsg = mapper.readValue(reader, ExceptionMessage.class);
                System.out.println("error:"+ eMsg.getCode() +"->" + eMsg.getMessage());
                return null;
            }
	        }
	        EntityUtils.consume(entity);
	        System.out.println("return null");
	        return null;
		}

}
