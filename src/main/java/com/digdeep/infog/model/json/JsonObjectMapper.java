package com.digdeep.infog.model.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class JsonObjectMapper<T> {
	
	public T getObject (JsonElement element, Class<T> objClass) throws Exception {
		Gson gson = new Gson();
		return gson.fromJson(element, objClass);
	}
}
