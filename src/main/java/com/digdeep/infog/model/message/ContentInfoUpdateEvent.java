package com.digdeep.infog.model.message;

import java.util.Calendar;
import java.util.List;

import com.digdeep.infog.model.ContentInfo;

public class ContentInfoUpdateEvent {

	private Calendar updateDate;
	
	private List<ContentInfo> updatedContent;

	public Calendar getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}

	public List<ContentInfo> getUpdatedContent() {
		return updatedContent;
	}

	public void setUpdatedContent(List<ContentInfo> updatedContent) {
		this.updatedContent = updatedContent;
	}
	
	
	
}
