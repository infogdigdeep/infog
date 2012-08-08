package com.digdeep.infog.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.json.ContentInfoAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FeedUtil {

	private Gson gson;
	
	public FeedUtil () {
		gson = new GsonBuilder().registerTypeAdapter(ContentInfo.class, new ContentInfoAdapter()).create();
	}
	
	public Gson getGson() {
		return gson;
	}


	public void setGson(Gson gson) {
		this.gson = gson;
	}


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
	
	public JsonObject getResponseData (JsonObject response) throws Exception {
		return (JsonObject) response.get("responseData");
	}
	
	public JsonArray getEntries (JsonObject responseData) throws Exception {
		return responseData.getAsJsonArray("entries");
	}
	
	
}
