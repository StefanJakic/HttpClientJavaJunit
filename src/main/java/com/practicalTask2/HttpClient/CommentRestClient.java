package com.practicalTask2.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentRestClient {

	public static final String ALL_COMMENTS_PATH = "/comments/all";
	public static final String ADD_COMMENT_PATH = "/comments/add";
	
	private Logger logger = LoggerFactory.getLogger(CommentRestClient.class);
	
	private String baseUrl = "";
	private CloseableHttpClient httpClient;

	public CommentRestClient(String baseUrl, CloseableHttpClient httpClient) {
		super();
		this.baseUrl = baseUrl;
		this.httpClient = httpClient;
	}

	public void getAllComments() throws ClientProtocolException, IOException {
		
		logger.info("Getting all comments");
		
		HttpGet httpGet = new HttpGet(baseUrl + ALL_COMMENTS_PATH);
		httpGet.setHeader("Accept", "application/json");
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		
		logger.info("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());
		//System.out.println("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());

		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();
		httpResponse.close();
		
		// print result
		
		logger.info(response.toString());
		//System.out.println(response.toString());

	}

	public void addComment(String name, String content) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(baseUrl + ADD_COMMENT_PATH);

		String jsonCommentTemplate = "(\"name\" : \"{0}\",\"content\" : \"{1}\")";

		StringEntity params = new StringEntity(MessageFormat.format(jsonCommentTemplate, name, content));

		httpPost.setEntity(params);
		httpPost.setHeader("Accept", "application/json"); //Client understood json
		httpPost.setHeader("Content-type", "application/json"); //Body content type
		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
		
		logger.info("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());
		//System.out.println("POST Response Status:: " + httpResponse.getStatusLine().getStatusCode());
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();
		httpResponse.close();
		// print result
		logger.info(response.toString());
		//System.out.println(response.toString());
	}

}
