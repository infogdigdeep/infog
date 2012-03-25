package com.digdeep.infog.exceptions;

import com.digdeep.infog.model.ContentType;

public class InvalidProviderException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidProviderException (int providerCode) {
		super(String.format("Invalid provider code specified %d.  Available mappings %s", 
					providerCode, getAllContentType()));
	}

	
	public static String getAllContentType() {
		ContentType[] allTypes = ContentType.values();
		StringBuilder result = new StringBuilder();
		for (int i=0; i < allTypes.length; i++) {
			if (i > 0) {
				result.append(", ");
			}
			result.append(allTypes[i].getType());
			result.append("=>");
			result.append(allTypes[i].toString());
		}
		return result.toString();
	}
}
