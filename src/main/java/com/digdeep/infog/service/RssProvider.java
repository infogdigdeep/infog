package com.digdeep.infog.service;

import java.util.List;

import javax.inject.Inject;

import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.qualifiers.ContentConfig;
import com.digdeep.infog.utils.ContentUtil;

@ContentConfig(contentType = ContentType.RSS)
public class RssProvider implements InfoProvider {

	@Inject
	private ContentUtil contentUtil;

	@Inject
	private YQLProvider yqlUtil;

	// http://rss.cbc.ca/lineup/technology.xml
	// http://weather.yahooapis.com/forecastrss?u=c
	public ContentSource get(ContentRequestInput input) throws Exception {
		if (input.getFeatureList() != null) {
			if (input.getFeatureList().isNeedWoeId()) {
				String woeid = yqlUtil.getWoeid(input);
				input.setUrl(input.getUrl() + "&w=" + woeid);
			}
		}
		ContentSource result = contentUtil.getContents(input.getUrl());

		return result;
	}
}
