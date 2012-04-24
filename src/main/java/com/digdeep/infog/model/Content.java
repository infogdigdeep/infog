package com.digdeep.infog.model;


public class Content {

	private long id;
	
	private String pictureUrl;
	
	private String title;
	
	private String summary;
	
	private String provider;

	private ContentType type;
	
	
	public Content(String pictureUrl, String title, String summary,
			String provider, ContentType type) {
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

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}
	
	
}
