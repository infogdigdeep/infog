package com.digdeep.infog.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentRequestFeature;
import com.digdeep.infog.model.input.ContentRequestInput;

public class GetContent {

	public static void main (String [] args) {
		PostMethod postMethod = null;
		try {
			
			HttpClient client = new HttpClient();
			postMethod = new PostMethod("http://localhost:8080/infog/service/content");
			ContentRequestInput reqInput = new ContentRequestInput();
			reqInput.setType(ContentType.RSS.getType());
			reqInput.setUrl("http://rss.cbc.ca/lineup/technology.xml");
			ContentRequestFeature feature = new ContentRequestFeature();
			feature.setNeedWoeId(false);
			reqInput.setFeatureList(feature);
			StringRequestEntity req = new StringRequestEntity(
					RestClient.getInstance().reqEntityToString(reqInput), "application/xml", "UTF-8");
			postMethod.setRequestEntity(req);
			client.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
				System.out.println(postMethod.getResponseBodyAsString());
			} else {
				System.out.println("Failed: " + postMethod.getStatusCode());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}
}
