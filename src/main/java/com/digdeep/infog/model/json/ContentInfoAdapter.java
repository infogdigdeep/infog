package com.digdeep.infog.model.json;

import java.lang.reflect.Type;

import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ContentInfoAdapter implements JsonDeserializer<ContentInfo> {

	@Override
	public ContentInfo deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		ContentInfo result = context.deserialize(jsonElement, type);
		result.setType(ContentType.RSS);
		return result;
	}

}
