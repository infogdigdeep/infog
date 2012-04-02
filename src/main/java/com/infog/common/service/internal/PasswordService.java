package com.infog.common.service.internal;

import java.security.MessageDigest;

public class PasswordService {

	public String hashDigestPassword(String user, String realmName, String password)
			throws Exception {
		String key = user + ":" + realmName + ":" + password;
		return hashPassword(key);
	}

	private String hashPassword(String password) throws Exception {
		MessageDigest md = (MessageDigest) MessageDigest.getInstance("MD5")
				.clone();
		md.reset();

		byte[] bytes = md.digest(password.getBytes());
		StringBuilder sb = new StringBuilder(2 * bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			int low = (int) (bytes[i] & 0x0f);
			int high = (int) ((bytes[i] & 0xf0) >> 4);
			sb.append(bytes[high]);
			sb.append(bytes[low]);
		}
		return sb.toString();
	}
}
