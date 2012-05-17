package com.digdeep.infog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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

	public byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte [] result = new byte[8];
		random.nextBytes(result);
		return result;
	}
	
	public byte [] encryptPBKDF2 (String target, byte [] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String algorithm = "PBKDF2WithHmacSHA1";
		int derivedKeyLength = 160;
		int iterations = 20000;
		KeySpec ks = new PBEKeySpec(target.toCharArray(), salt, iterations, derivedKeyLength);
		SecretKeyFactory kf = SecretKeyFactory.getInstance(algorithm);
		return kf.generateSecret(ks).getEncoded();
	}
	
	public static void main (String args[]) {
		try {
			EncryptionUtil util = new EncryptionUtil();
			System.out.println(util.encryptPBKDF2("testing", util.getSalt()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
