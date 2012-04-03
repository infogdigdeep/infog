package com.digdeep.infog.beans.controller;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.digdeep.infog.external.ContentProvision;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ConfigInput;
import com.digdeep.infog.model.input.ContentProvisionInput;

@RequestScoped
@Model
public class Content {

	@Inject
	private ContentProvision provisionService;
	
	@Inject
	private ContentInfo currentInfo;
	
	private List<ContentInfo> contentList;

	public ContentType[] getContentTypeValues() {
		return ContentType.values();
	}

	public void save() {
		ConfigInput config = new ConfigInput();
		ContentProvisionInput input = new ContentProvisionInput();
		input.setDescription(currentInfo.getDescriptions());
		input.setType(currentInfo.getType().getType());
		input.setUrl(currentInfo.getUrl());
		config.setContentInput(input);
		provisionService.addConfig(config);
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


}
