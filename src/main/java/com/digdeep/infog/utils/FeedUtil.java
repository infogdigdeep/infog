package com.digdeep.infog.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FeedUtil {

	public JsonObject getObjectFromURL (String urlStr) throws Exception {
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		conn.addRequestProperty("Referer", "infog-digdeep.rhcloud.com");
		StringBuilder result = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		JsonParser parser = new JsonParser();
		return (JsonObject) parser.parse(result.toString());
	}
	
	public JsonElement getResponseData (JsonObject response) throws Exception {
		return response.get("responseData");
		
	}
}
