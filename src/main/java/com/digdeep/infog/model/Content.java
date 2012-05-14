package com.digdeep.infog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Entity
public class Content {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String pictureUrl;
	
	private String title;
	
	private String summary;
	
	@Temporal(TemporalType.TIME)
	private Date pubDate;
	
	@ManyToOne
	private ContentSource provider;

	private ContentType type;
	
	
	public Content() {
		
	}
	
	public Content(String pictureUrl, String title, String summary,
			ContentSource provider, ContentType type) {
		super();
		this.pictureUrl = pictureUrl;
		this.title = title;
		this.summary = summary;
		this.provider = provider;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public ContentSource getProvider() {
		return provider;
	}

	public void setProvider(ContentSource provider) {
		this.provider = provider;
	}

	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
	
}
