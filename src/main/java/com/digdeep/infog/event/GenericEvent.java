package com.digdeep.infog.event;

import java.util.List;

import com.digdeep.infog.model.ContentSource;

public class GenericEvent {

	private List<ContentSource> contents;

	public List<ContentSource> getContents() {
		return contents;
	}

	public void setContents(List<ContentSource> contents) {
		this.contents = contents;
	}
	
	
}
