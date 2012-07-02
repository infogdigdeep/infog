package com.digdeep.infog.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.digdeep.infog.external.soap.ContentProvider;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentRequestInput;

public class ContentProviderTest {

	private ContentProvider contentProvider;
	
	@Before
	public void before () {
		try {
			
			URL wsdlURL = new URL("http://localhost:7001/infog/cxf/ContentProvider?wsdl");
			QName serviceName = new QName("http://infog.digdeep.com", "ContentProvider");
			Service service = Service.create(wsdlURL, serviceName);
			contentProvider = (ContentProvider) service.getPort(ContentProvider.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	@Test
	public void getContent() throws Exception {
		ContentRequestInput input = new ContentRequestInput();
		input.setType(ContentType.RSS.getType());
		List<ContentSource> ctList = contentProvider.getInfo(input);
		Assert.assertNotNull(ctList);
	}
}
