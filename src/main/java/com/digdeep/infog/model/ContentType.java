package com.digdeep.infog.model;

import javax.enterprise.util.AnnotationLiteral;

import com.digdeep.infog.qualifiers.ContentConfig;

public enum ContentType {
	WEATHER(0), NEWS(1);
	
	private int type;
	
	private ContentType (int type) {
		this.type = type;
	}
	
	public static ContentType getType (int type) {
		switch (type) {
		case 0:
			return WEATHER;
		case 1:
			return NEWS;
		default:
			return WEATHER;
		}
	}
	
	public int getType() {
		return type;
	}
	

}
