package com.digdeep.infog.beans.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;


import com.digdeep.infog.external.ContentProvision;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ConfigInput;
import com.digdeep.infog.model.input.ContentProvisionInput;
import com.digdeep.infog.service.data.ContentInfoService;


@ManagedBean
@ViewScoped
public class Content {
	
	@Inject
	private ContentProvision provisionService;
	
	@Inject
	private ContentInfo currentInfo;
	
	@EJB
	private ContentInfoService infoService;
	
	private List<ContentInfo> contentList;

	private boolean isAdd;

	private String query;
	
	public ContentType[] getContentTypeValues() {
		return ContentType.values();
	}

	public void save() throws Exception {
		ConfigInput config = new ConfigInput();
		ContentProvisionInput input = new ContentProvisionInput();
		input.setDescription(currentInfo.getDescriptions());
		input.setType(currentInfo.getType().getType());
		input.setUrl(currentInfo.getUrl());
		config.setContentInput(input);
		provisionService.addConfig(config);
		resetCurrentInfo();

	}

	public void delete() {
		infoService.delete(currentInfo);
	}

	public void resetCurrentInfo() {
		setCurrentInfo(new ContentInfo());
	}

	public ContentInfo getCurrentInfo() {
		return currentInfo;
	}

	public void setCurrentInfo(ContentInfo currentInfo) {
		this.currentInfo = currentInfo;
	}

	public List<ContentInfo> getContentList() {
		return provisionService.getAllContentInfo();
	}

	public void setContentList(List<ContentInfo> contentList) {
		this.contentList = contentList;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	
}
