package com.digdeep.infog.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;



@Entity
public class ContentInfo {
	
	@Id
	@GeneratedValue
	private long id;

	private String descriptions;

	@NotNull
	private String url;
	
	@Enumerated
	private ContentType type;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	
	
}
