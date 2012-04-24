package com.digdeep.infog.model.json;

import com.digdeep.infog.model.Content;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ModelJsonHandler {

	public String toJson (Content content) {
		String result = null;
		/*
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Content.class, new ContentAdapter());
		Gson gson = builder.create();
		*/
		Gson gson = new Gson();
		result = gson.toJson(content);
		return result;
	}
	
	public Content fromJson (String content){
		/*
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Content.class, new ContentAdapter());
		Gson gson = builder.create();
		*/
		Gson gson = new Gson();
		return gson.fromJson(content, Content.class);
	}
}
