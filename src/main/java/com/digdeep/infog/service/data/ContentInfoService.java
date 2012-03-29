package com.digdeep.infog.service.data;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import com.digdeep.infog.dao.ContentInfoDao;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentProvisionInput;

@RequestScoped
public class ContentInfoService {

	@Inject
	private ContentInfoDao dao;
	
	public void save(ContentProvisionInput input) {
		ContentInfo contentInfo = new ContentInfo();
		contentInfo.setType(ContentType.getType(input.getType()));
		contentInfo.setUrl(input.getUrl());
		contentInfo.setDescriptions(input.getDescription());
		dao.save(contentInfo);
	}
}
