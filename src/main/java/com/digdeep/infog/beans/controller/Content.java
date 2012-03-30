package com.digdeep.infog.beans.controller;

import javax.enterprise.inject.Model;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.digdeep.infog.external.ContentProvision;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentProvisionInput;

@RequestScoped
@Model
public class Content {

	@Inject
	private ContentProvision provisionService;
	
	@Inject
	private ContentInfo currentInfo;

	public ContentType[] getContentTypeValues() {
		return ContentType.values();
	}

	public void save() {
		ContentProvisionInput input = new ContentProvisionInput();
		input.setDescription(currentInfo.getDescriptions());
		input.setType(currentInfo.getType().getType());
		input.setUrl(currentInfo.getUrl());
		provisionService.addContentInfo(input);
	}

	public ContentInfo getCurrentInfo() {
		return currentInfo;
	}

	public void setCurrentInfo(ContentInfo currentInfo) {
		this.currentInfo = currentInfo;
	}
	
	
}
