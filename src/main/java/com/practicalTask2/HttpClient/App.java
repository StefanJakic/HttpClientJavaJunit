package com.practicalTask2.HttpClient;

import java.io.IOException;

import org.apache.http.impl.client.HttpClients;

public class App 
{
    public static void main( String[] args )
    {
    	CommentRestClient commentRestClient = new CommentRestClient("http://localhost:8080", HttpClients.createDefault());
    	
    	try {
			commentRestClient.getAllComments();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
