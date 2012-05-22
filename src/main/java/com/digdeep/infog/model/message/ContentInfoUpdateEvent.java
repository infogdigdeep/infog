package com.digdeep.infog.model.message;

import java.util.Calendar;

import com.digdeep.infog.model.ContentSource;

public class ContentInfoUpdateEvent {

	private Calendar updateDate;
	
	private ContentSource contentSource;

	public Calendar getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}

	public ContentSource getContentSource() {
		return contentSource;
	}

	public void setContentSource(ContentSource contentSource) {
		this.contentSource = contentSource;
	}
	
	
	
	
}
