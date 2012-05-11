package com.digdeep.infog.service;


import javax.inject.Inject;

import net.sf.json.JSONObject;


import com.digdeep.infog.utils.ContentUtil;

public class JSONProvider {

	@Inject
	private ContentUtil contentUtil;
	
	//http://openexchangerates.org/latest.json"
	public JSONObject get(String url) {
		JSONObject result = null;
		try {
			result = contentUtil.parseJSON(url);
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		return result;
	}
	
	
}
