package com.digdeep.infog.service;


import javax.inject.Inject;


import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.qualifiers.ContentConfig;
import com.digdeep.infog.utils.ContentUtil;


@ContentConfig(contentType=ContentType.WEATHER)
public class WeatherProvider implements InfoProvider {

	@Inject
	private ContentUtil contentUtil;
	
	@Inject
	private YQLProvider yqlUtil;
	
	public String get(ContentRequestInput input) throws Exception {	
		String woeid = yqlUtil.get(input);
		return contentUtil.getConcatDescriptions("http://weather.yahooapis.com/forecastrss?u=c&w=" + woeid);
	}



}
