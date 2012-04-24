package com.digdeep.infog.model.json;

import java.lang.reflect.Type;

import com.digdeep.infog.model.Content;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public class ContentAdapter implements JsonSerializer<Content>, JsonDeserializer<Content> {

	@Override
	public Content deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		return context.deserialize(jsonElement, type);
	}

	@Override
	public JsonElement serialize(Content content, Type type,
			JsonSerializationContext context) {
		return context.serialize(content, type);
	}
	

}
