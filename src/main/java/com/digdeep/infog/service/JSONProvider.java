package com.digdeep.infog.service;

import java.io.InputStream;

import javax.inject.Inject;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;

import com.digdeep.infog.utils.ContentUtil;

public class JSONProvider {

	@Inject
	private ContentUtil contentUtil;
	
	//http://openexchangerates.org/latest.json"
	public JSONObject get(String url) {
		JSONObject result = null;
		try {
			InputStream content = contentUtil.getContentStream(url);
			String contentStr = IOUtils.toString(content);
			result = contentUtil.parseJSON(contentStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		return result;
	}
	
	
}
