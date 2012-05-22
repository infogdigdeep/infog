package com.digdeep.infog.service.data;


import java.util.Calendar;
import java.util.Collections;

import javax.ejb.ApplicationException;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.digdeep.infog.beans.InfoProviderBean;
import com.digdeep.infog.dao.GenericDao;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentProvisionInput;
import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.model.message.ContentInfoUpdateEvent;

@Stateless
public class ContentInfoService extends GenericDao<ContentInfo> {
	

	@Inject
	private InfoProviderBean provider;

	@Inject
	private Event<ContentInfoUpdateEvent> contentUpdateEvent;

	public ContentInfoService() {
		super(ContentInfo.class);
		// TODO Auto-generated constructor stub
	}

	public void save(ContentProvisionInput input) throws Exception {
		ContentInfo contentInfo = new ContentInfo();
		contentInfo.setType(ContentType.getType(input.getType()));
		contentInfo.setUrl(input.getUrl());
		contentInfo.setDescriptions(input.getDescription());
		save(contentInfo);
		
		ContentRequestInput reqInput = new ContentRequestInput();
		reqInput.setUrl(contentInfo.getUrl());
		reqInput.setType(contentInfo.getType().getType());
		ContentSource src = provider.get(reqInput);

		ContentInfoUpdateEvent iEvent = new ContentInfoUpdateEvent();
		iEvent.setUpdateDate(Calendar.getInstance());
		iEvent.setContentSource(src);
		contentUpdateEvent.fire(iEvent);
	}
	
	public void storeStaticContentInfo() {
		ContentInfo i1 = new ContentInfo("CBC Technology", "http://rss.cbc.ca/lineup/technology.xml", ContentType.RSS);
		ContentInfo i2 = new ContentInfo("CBC Top Stories", "http://rss.cbc.ca/lineup/topstories.xml", ContentType.RSS);
		save(i1);
		save(i2);
	}
	
}
