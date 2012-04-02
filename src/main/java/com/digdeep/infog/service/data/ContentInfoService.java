package com.digdeep.infog.service.data;


import javax.ejb.Stateless;

import com.digdeep.infog.dao.GenericDao;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentProvisionInput;

@Stateless
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
	
}
