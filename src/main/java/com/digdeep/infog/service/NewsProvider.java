package com.digdeep.infog.service;

import javax.inject.Inject;

import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.qualifiers.ContentConfig;
import com.digdeep.infog.utils.ContentUtil;



@ContentConfig(contentType=ContentType.NEWS)
public class NewsProvider implements InfoProvider {

	@Inject
	private ContentUtil contentUtil;
	
	//http://rss.cbc.ca/lineup/technology.xml
	public String get(ContentRequestInput input) throws Exception {	
		return contentUtil.getConcatDescriptions(input.getUrl());
	}
}
