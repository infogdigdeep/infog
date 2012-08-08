package com.digdeep.infog.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;



@XmlRootElement
@Entity
@Table(name="contentInfo")
public class ContentInfo {
	
	
	public ContentInfo() {

	}

	public ContentInfo(String descriptions, String url, ContentType type) {
		super();
		this.descriptions = descriptions;
		this.url = url;
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@SerializedName("title")
	private String descriptions;

	@NotNull
	@SerializedName("url")
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
