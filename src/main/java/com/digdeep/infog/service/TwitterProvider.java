package com.digdeep.infog.service;

import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ApplicationScoped;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

@ApplicationScoped
public class TwitterProvider {

	private HashMap<Long, AccessToken> accessTokenStore = new HashMap<Long, AccessToken>(
			10);

	public AccessToken getAccessToken(String consumerKey,
			String consumerSecret, String pin) throws Exception {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		RequestToken requestToken = twitter.getOAuthRequestToken();
		String authUrl = requestToken.getAuthenticationURL();
		System.out.println("Auth Url: " + authUrl);
		AccessToken accessToken = twitter
				.getOAuthAccessToken(requestToken, pin);
		long userId = twitter.verifyCredentials().getId();
		// twitter.updateStatus("");
		accessTokenStore.put(userId, accessToken);
		return accessToken;

	}

	public Twitter getTwitter(String userId, String consumerKey,
			String consumerSecret, String pin) throws Exception {
		AccessToken userToken = null;
		if (!accessTokenStore.containsKey(userId)) {
			userToken = getAccessToken(consumerKey, consumerSecret, pin);
		} else {
			userToken = accessTokenStore.get(userId);
		}
		ConfigurationBuilder confbuilder = new ConfigurationBuilder();
		confbuilder.setOAuthAccessToken(userToken.getToken())
				.setOAuthAccessTokenSecret(userToken.getTokenSecret())
				.setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret);
		Twitter twitter = new TwitterFactory(confbuilder.build()).getInstance();
		return twitter;
	}
	
	public List<Tweet> searchTweet(String userId, String consumerKey,
			String consumerSecret, String pin, String query) throws Exception {
		Twitter twitter = getTwitter(userId, consumerKey, consumerSecret, pin);
		Query tQuery = new Query(query);
		QueryResult result = twitter.search(tQuery);
		return result.getTweets();
	}
}
