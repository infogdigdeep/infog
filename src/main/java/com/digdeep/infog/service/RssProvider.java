package com.digdeep.infog.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.digdeep.infog.model.Content;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.qualifiers.ContentConfig;
import com.digdeep.infog.utils.ContentUtil;



@ContentConfig(contentType=ContentType.RSS)
public class RssProvider implements InfoProvider {

	@Inject
	private ContentUtil contentUtil;
	
	@Inject
	private YQLProvider yqlUtil;
	
	//http://rss.cbc.ca/lineup/technology.xml
	//http://weather.yahooapis.com/forecastrss?u=c
	public List<Content> get(ContentRequestInput input) throws Exception {	
		if (input.getFeatureList().isNeedWoeId()) {
			String woeid = yqlUtil.getWoeid(input);
			input.setUrl(input.getUrl() + "&w=" +woeid);
		}
		List<Content> result = new ArrayList<Content>();
		
		return result;
	}
}
