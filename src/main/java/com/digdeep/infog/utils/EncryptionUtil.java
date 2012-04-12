package com.digdeep.infog.utils;

import java.security.MessageDigest;

public class EncryptionUtil {
	
	public String encryptSHA256 (String target) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(target.getBytes());
		
        byte[] mdbytes = md.digest();
        
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return sb.toString();

	}

}
