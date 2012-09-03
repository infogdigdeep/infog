package com.digdeep.infog.beans.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.digdeep.infog.beans.FeedDiscoveryBeanLocal;
import com.digdeep.infog.beans.FeedDiscoveryBeanRemote;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.json.JsonObjectMapper;
import com.digdeep.infog.utils.ContentUtil;
import com.digdeep.infog.utils.FeedUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
@Stateless
@Remote(FeedDiscoveryBeanRemote.class)
@Local(FeedDiscoveryBeanLocal.class)
public class FeedDiscoveryBeanImpl implements FeedDiscoveryBeanLocal,
		FeedDiscoveryBeanRemote {

	
	private static final String findFeedAPI = "https://ajax.googleapis.com/ajax/services/feed/find";

	@Inject
	private ContentUtil contentUtil;

	public String buildFeedUrl(String query) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append(findFeedAPI);
		builder.append("?v=1.0&q=");
		builder.append(URLEncoder.encode(query, "UTF-8"));
		return builder.toString();
	}
	
	@Override
	public List<ContentInfo> findFeeds(String query) {
		List<ContentInfo> result = null;
		try {
			FeedUtil util = new FeedUtil();
			JsonObject response = util.getObjectFromURL(buildFeedUrl(query));
			result = mapToContentInfoList(util, util.getEntries(util.getResponseData(response)));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<ContentSource> findFeedsInfo (String query) {
		List<ContentSource> result = null;
		try {
			result = new ArrayList<ContentSource>();
			List<ContentInfo> cInfo = findFeeds(query);
			for (ContentInfo tmpCInfo : cInfo) {
				result.add(contentUtil.getContents(tmpCInfo.getUrl()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	private List<ContentInfo> mapToContentInfoList (FeedUtil util, JsonArray entries) throws Exception {
		List<ContentInfo> result = new ArrayList<ContentInfo>();
		Iterator<JsonElement> entriesItr = entries.iterator();
		JsonObjectMapper<ContentInfo> mapper = new JsonObjectMapper<ContentInfo>();
		while (entriesItr.hasNext()) {
			JsonElement entry = entriesItr.next(); 
			result.add(mapper.getObject(entry,ContentInfo.class));
		}
		return result;
	}

	public ContentUtil getContentUtil() {
		return contentUtil;
	}

	public void setContentUtil(ContentUtil contentUtil) {
		this.contentUtil = contentUtil;
	}
	

}
