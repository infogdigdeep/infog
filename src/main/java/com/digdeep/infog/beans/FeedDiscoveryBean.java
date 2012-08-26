package com.digdeep.infog.beans;

import java.util.List;

import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentSource;

public interface FeedDiscoveryBean {

	public List<ContentInfo> findFeeds (String query);
	
	public List<ContentSource> findFeedsInfo (String query);
	
}
