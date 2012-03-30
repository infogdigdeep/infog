package com.digdeep.infog.beans.controller;

import javax.enterprise.inject.Model;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentType;

@RequestScoped
@Model
public class Content {

	@Inject
	private ContentInfo currentInfo;

	public ContentType[] getContentTypeValues() {
		return ContentType.values();
	}

	public void save() {

	}
}
