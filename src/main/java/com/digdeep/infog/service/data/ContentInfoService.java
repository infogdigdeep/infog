package com.digdeep.infog.service.data;


import javax.ejb.ApplicationException;
import javax.ejb.Stateless;

import com.digdeep.infog.dao.GenericDao;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentProvisionInput;

@Stateless
@ApplicationException(rollback=true)
public class ContentInfoService extends GenericDao<ContentInfo> {

	public ContentInfoService() {
		super(ContentInfo.class);
		// TODO Auto-generated constructor stub
	}

	public void save(ContentProvisionInput input) {
		ContentInfo contentInfo = new ContentInfo();
		contentInfo.setType(ContentType.getType(input.getType()));
		contentInfo.setUrl(input.getUrl());
		contentInfo.setDescriptions(input.getDescription());
		save(contentInfo);
	}
	
	public void storeStaticContentInfo() {
		ContentInfo i1 = new ContentInfo("CBC Technology", "http://rss.cbc.ca/lineup/technology.xml", ContentType.NEWS);
		ContentInfo i2 = new ContentInfo("CBC Top Stories", "http://rss.cbc.ca/lineup/topstories.xml", ContentType.NEWS);
		save(i1);
		save(i2);
	}
	
}
