package com.infog.common.service.internal;

import java.security.MessageDigest;

public class PasswordService {

	public String hashDigestPassword(String user, String realmName,
			String password) throws Exception {
		String key = user + ":" + realmName + ":" + password;
		return hashPassword(key);
	}

	private static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private String hashPassword(String password) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();

		byte[] bytes = md.digest(password.getBytes());
		StringBuilder sb = new StringBuilder(2 * bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			int low = (int) (bytes[i] & 0x0f);
			int high = (int) ((bytes[i] & 0xf0) >> 4);
			sb.append(HEXADECIMAL[high]);
			sb.append(HEXADECIMAL[low]);
		}
		return sb.toString();
	}

}
