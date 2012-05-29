package com.digdeep.infog.test;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.utils.ContentUtil;

public class ContentUtilTest {

	private ContentUtil util = null;
	
	@BeforeClass
	public void before() {
		util = new ContentUtil();
	}

	@Test
	public void testGetContent() throws Exception {
		try {
			ContentSource src = util.getContents("http://rss.cbc.ca/lineup/topstories.xml");
			Assert.assertNotNull(src);
			Assert.assertFalse(src.getContents().isEmpty());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
	}
	
	@AfterClass
	public void after() {
		
	}
}
