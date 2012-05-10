package com.digdeep.infog.model;



public enum ContentType {
	RSS(0), JSON(1);
	
	private int type;
	
	private ContentType (int type) {
		this.type = type;
	}
	
	public static ContentType getType (int type) {
		switch (type) {
		case 0:
			return RSS;
		case 1:
			return JSON;
		default:
			return RSS;
		}
	}
	
	public int getType() {
		return type;
	}
	

}
