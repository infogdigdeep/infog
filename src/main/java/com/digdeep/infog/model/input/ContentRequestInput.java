package com.digdeep.infog.model.input;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContentRequestInput {

	private int type;
	private String zipCode;
	private String query;

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	
}
