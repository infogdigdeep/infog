package com.digdeep.infog.test;


import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.digdeep.infog.beans.FeedDiscoveryBean;
import com.digdeep.infog.beans.FeedDiscoveryBeanRemote;
import com.digdeep.infog.beans.impl.FeedDiscoveryBeanImpl;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {" file:src/test/resource/test-context.xml"})
public class ContentDiscoveryTest {

	@Autowired
	private FeedDiscoveryBeanImpl feedDiscoveryBean;
	
	@Test
	public void discover() {
		List<ContentInfo> result = feedDiscoveryBean.findFeeds("Google");
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size() > 0);
	}
	
	@Test
	public void findFeeds() {
		List<ContentSource> result = feedDiscoveryBean.findFeedsInfo("Google");
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size() > 0);
	}
}
