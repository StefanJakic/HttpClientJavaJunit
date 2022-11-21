package com.practicalTask2.HttpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class CommentRestClientTest {

	private static final int PORT = 8080;
	private static final String BASE_URL = "http://localhost:" + PORT;

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(PORT);

	@Test
	public void isGetAllCommentsSuccessfulForHttp200() {
		stubFor(get(CommentRestClient.ALL_COMMENTS_PATH).willReturn(ok(
				"[(\"name\" : \"Stefan\",\"content\" : \"Java is great\"),(\"name\" : \"Anastasia\",\"content\" : \"Good job\")]")
				.withHeader("Content-Type", "application/json")));

		CommentRestClient commentRestClient = new CommentRestClient(BASE_URL, HttpClients.createDefault());

		try {
			commentRestClient.getAllComments();
		} catch (IOException e) {
			e.printStackTrace();
		}

		verify(getRequestedFor(urlEqualTo(CommentRestClient.ALL_COMMENTS_PATH)).withHeader("Accept",
				equalTo("application/json")));
	}

	@Test
	public void isPostCommentIsSuccessfulForHttp200() {
		stubFor(post(CommentRestClient.ADD_COMMENT_PATH).willReturn(ok("(\"success\": true)")));

		CommentRestClient commentRestClient = new CommentRestClient(BASE_URL, HttpClients.createDefault());

		String commentSenderName = "Stefan";
		String commentContent = "Java is great";

		try {
			commentRestClient.addComment(commentSenderName, commentContent);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
		// What is format style use in orioninc?
		verify(postRequestedFor(urlEqualTo(CommentRestClient.ADD_COMMENT_PATH))
				.withHeader("Content-Type", equalTo("application/json"))
				.withHeader("Accept", equalTo("application/json")).withRequestBody(containing(commentSenderName))
				.withRequestBody(containing(commentContent)));
	}
	
	@Test
	public void isGetAllCommentsSuccessfulForHttp404() {
		stubFor(get(CommentRestClient.ALL_COMMENTS_PATH).willReturn(notFound()));

		CommentRestClient commentRestClient = new CommentRestClient(BASE_URL, HttpClients.createDefault());

		try {
			commentRestClient.getAllComments();
		} catch (IOException e) {
			e.printStackTrace();
		}

		verify(getRequestedFor(urlEqualTo(CommentRestClient.ALL_COMMENTS_PATH)).withHeader("Accept",
				equalTo("application/json")));
	}
	
	@Test
	public void isGetAllCommentsSuccessfulForHttp400() {
		stubFor(get(CommentRestClient.ALL_COMMENTS_PATH).willReturn(badRequest()));

		CommentRestClient commentRestClient = new CommentRestClient(BASE_URL, HttpClients.createDefault());

		try {
			commentRestClient.getAllComments();
		} catch (IOException e) {
			e.printStackTrace();
		}

		verify(getRequestedFor(urlEqualTo(CommentRestClient.ALL_COMMENTS_PATH)).withHeader("Accept",
				equalTo("application/json")));
	}
	
	
}
