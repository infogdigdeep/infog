package com.digdeep.infog.model;



public enum ContentType {
	RSS(0), ATOM(1), JSON(2);
	
	private int type;
	
	private ContentType (int type) {
		this.type = type;
	}
	
	public static ContentType getType (int type) {
		switch (type) {
		case 0:
			return RSS;
		case 1:
			return ATOM;
		case 2:
			return JSON;
		default:
			return RSS;
		}
	}
	
	public int getType() {
		return type;
	}
	

}
