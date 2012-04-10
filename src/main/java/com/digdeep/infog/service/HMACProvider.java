package com.digdeep.infog.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HMACProvider {

	private final static String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";
	private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	private final static String SECRET = "secretsecret";
	private final static String USERNAME = "jos";

	private static final Logger LOG = LoggerFactory
			.getLogger(HMACProvider.class);

	public static void main(String[] args) throws HttpException, IOException,
			NoSuchAlgorithmException {
		HMACProvider client = new HMACProvider();
		client.makeHTTPCallUsingHMAC(USERNAME);
	}

	public void makeHTTPCallUsingHMAC(String username) throws HttpException,
			IOException, NoSuchAlgorithmException {
		String contentToEncode = "{\"comment\" : {\"message\":\"blaat\" , \"from\":\"blaat\" , \"commentFor\":123}}";
		String contentType = "application/vnd.geo.comment+json";
		// String contentType = "text/plain";
		String currentDate = new SimpleDateFormat(DATE_FORMAT)
				.format(new Date());

		PostMethod post = new PostMethod(
				"http://localhost:9000/resources/rest/geo/comment");
		StringRequestEntity data = new StringRequestEntity(contentToEncode, contentType,
				"UTF-8");
		post.setRequestEntity(data);

		String verb = post.getName();
		String contentMd5 = calculateMD5(contentToEncode);
		String toSign = verb + "\n" + contentMd5 + "\n"
				+ data.getContentType() + "\n" + currentDate + "\n"
				+ post.getURI().getPath();

		String hmac = calculateHMAC(SECRET, toSign);

		post.addRequestHeader("hmac", username + ":" + hmac);
		post.addRequestHeader("Date", currentDate);
		post.addRequestHeader("Content-Md5", contentMd5);

		HttpClient client = new HttpClient();
		int statusCode = client.executeMethod(post);
		System.out.println("client response:"
				+ statusCode + ": " + post.getResponseBodyAsString());
	}

	private String calculateHMAC(String secret, String data) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(),
					HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			String result = new String(Base64.encodeBase64(rawHmac));
			return result;
		} catch (GeneralSecurityException e) {
			LOG.warn("Unexpected error while creating hash: " + e.getMessage(),
					e);
			throw new IllegalArgumentException();
		}
	}

	private String calculateMD5(String contentToEncode)
			throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(contentToEncode.getBytes());
		String result = new String(Base64.encodeBase64(digest.digest()));
		return result;
	}
}
