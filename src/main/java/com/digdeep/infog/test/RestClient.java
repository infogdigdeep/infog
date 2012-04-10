package com.digdeep.infog.test;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ConfigInput;
import com.digdeep.infog.model.input.ContentProvisionInput;
import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.model.input.ControlProvisionInput;

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
	public static void main(String[] args) {
		PutMethod putMethod = null;
		try {
			RestClient coreClient = new RestClient();
			HttpClient client = new HttpClient();
			putMethod = new PutMethod(
					"http://localhost:8080/infog/service/config");
			
			ControlProvisionInput ctlInput = new ControlProvisionInput();
			ctlInput.setUsername("admin");
			ctlInput.setEmail("admin@infog.com");
			ctlInput.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
			ctlInput.setGroupname("ADMIN");
			ContentProvisionInput provInput = new ContentProvisionInput();
			provInput.setDescription("Google News RSS");
			provInput.setType(0);
			provInput.setUrl("http://news.google.com/news?ned=us&topic=h&output=rss");
			
			ConfigInput input = new ConfigInput();
			input.setContentInput(provInput);
			input.setControlInput(ctlInput);
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
	}
	/*
	public static void main(String[] args) {
		PostMethod postMethod = null;
		try {
			RestClient coreClient = new RestClient();
			HttpClient client = new HttpClient();
			postMethod = new PostMethod(
					"http://localhost:8080/infog/service/content");
			ContentRequestInput req1 = new ContentRequestInput();
			req1.setType(0);
			req1.setZipCode("L3R0B2");
			StringRequestEntity req = new StringRequestEntity(
					coreClient.reqEntityToString(req1), "application/xml", "UTF-8");
			postMethod.setRequestEntity(req);
			client.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
				System.out.println(postMethod.getResponseBodyAsString());
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			postMethod.releaseConnection();

		}
	}
	*/
}
