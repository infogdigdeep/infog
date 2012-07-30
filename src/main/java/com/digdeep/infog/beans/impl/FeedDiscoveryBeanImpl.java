package com.digdeep.infog.beans.impl;

import java.net.URLEncoder;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.digdeep.infog.beans.FeedDiscoveryBean;
import com.digdeep.infog.beans.FeedDiscoveryBeanLocal;
import com.digdeep.infog.beans.FeedDiscoveryBeanRemote;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.utils.FeedUtil;
import com.google.gson.JsonObject;

@Stateless
@Remote(FeedDiscoveryBeanRemote.class)
@Local(FeedDiscoveryBeanLocal.class)
public class FeedDiscoveryBeanImpl implements FeedDiscoveryBeanLocal,
		FeedDiscoveryBeanRemote {

	private static final String findFeedAPI = "https://ajax.googleapis.com/ajax/services/feed/find";

	@Override
	public List<ContentInfo> findFeeds(String query) {
		List<ContentInfo> result = null;
		try {
			FeedUtil util = new FeedUtil();
			StringBuilder builder = new StringBuilder();
			builder.append(findFeedAPI);
			builder.append(URLEncoder.encode(query, "UTF-8"));
			JsonObject response = util.getObjectFromURL(findFeedAPI);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
