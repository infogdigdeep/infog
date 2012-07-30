package com.digdeep.infog.beans;

import java.util.List;

import com.digdeep.infog.model.ContentInfo;

public interface FeedDiscoveryBean {

	public List<ContentInfo> findFeeds (String query);
	
}
