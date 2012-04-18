package com.digdeep.infog.model.input;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContentProvisionInput {

	private int type;
	
	private String description;
	
	private String url;
	


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	
	
}
