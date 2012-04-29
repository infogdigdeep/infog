package com.digdeep.infog.test;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ConfigInput;
import com.digdeep.infog.model.input.ContentProvisionInput;
import com.digdeep.infog.model.input.ContentRequestInput;

public class RestClient {

	public String reqEntityToString(Object input) throws Exception {

		JAXBContext context = JAXBContext.newInstance(input.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		StringWriter sw = new StringWriter();
		marshaller.marshal(input, sw);

		return sw.toString();
	}
	/*
	public static void main(String[] args) {
		PutMethod putMethod = null;
		try {
			RestClient coreClient = new RestClient();
			HttpClient client = new HttpClient();
			putMethod = new PutMethod(
					"http://localhost:18080/infog/service/config");
			

			ConfigInput input = new ConfigInput();
			StringRequestEntity req = new StringRequestEntity(
					coreClient.reqEntityToString(input), "application/xml", "UTF-8");
			putMethod.setRequestEntity(req);
			client.executeMethod(putMethod);
			if (putMethod.getStatusCode() == HttpStatus.SC_OK) {
				System.out.println(putMethod.getResponseBodyAsString());
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			putMethod.releaseConnection();

		}
	}*/
	
	//http://rss.cbc.ca/lineup/technology.xml
	//http://weather.yahooapis.com/forecastrss?u=c
	public static void main(String[] args) {
		PostMethod postMethod = null;
		try {
			RestClient coreClient = new RestClient();
			HttpClient client = new HttpClient();
			postMethod = new PostMethod("http://localhost:7001/infog/service/config");
			ConfigInput config = new ConfigInput();
			ContentProvisionInput contentConfig = new ContentProvisionInput();
			contentConfig.setDescription("CBC Technology");
			contentConfig.setType(ContentType.RSS.getType());
			contentConfig.setUrl("http://rss.cbc.ca/lineup/technology.xml");
			config.setContentInput(contentConfig);
			StringRequestEntity req = new StringRequestEntity(
					coreClient.reqEntityToString(config), "application/xml", "UTF-8");
			postMethod.setRequestEntity(req);
			client.executeMethod(postMethod);
			
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
				System.out.println(postMethod.getResponseBodyAsString());
			} else {
				System.out.println("Failed: " + postMethod.getStatusCode());
			}
			
			postMethod = new PostMethod("http://localhost:18080/infog/service/content");
			ContentRequestInput req1 = new ContentRequestInput();
			req1.setZipCode("L3R1V8");
			req1.setType(0);
			req = new StringRequestEntity(
					coreClient.reqEntityToString(req1), "application/xml", "UTF-8");
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
