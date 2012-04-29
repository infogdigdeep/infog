package com.digdeep.infog.service;

import java.net.URLEncoder;

import javax.inject.Inject;

import net.sf.json.JSONObject;

import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.utils.ContentUtil;

public class YQLProvider {

	//http://query.yahooapis.com/v1/public/YQL?format=json&q=
	//select woeid from geo.places where text='$zip' limit 1
	
	@Inject
	private ContentUtil contentUtil;
	
	public String getWoeidQuery(String zipCode) {
	    String zipQuery = String.format("select woeid from geo.places where text='%s' limit 1", zipCode);
		return zipQuery;
		
	}

	public String getWoeid(ContentRequestInput input) throws Exception {
		String url = "http://query.yahooapis.com/v1/public/yql?format=json&q=";
		String query = getWoeidQuery(input.getZipCode());
		url += URLEncoder.encode(query, "UTF-8");
		JSONObject queryResultObj = contentUtil.parseJSON(url);
		return queryResultObj.getJSONObject("query").getJSONObject("results").getJSONObject("place").getString("woeid");
	}
}
