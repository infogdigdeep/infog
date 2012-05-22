package com.digdeep.infog.utils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.digdeep.infog.model.Content;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.qualifiers.XMLLogging;

public class ContentUtil {

	private GetMethod getMethod;
	private PostMethod postMethod;

	public GetMethod getGetMethod() {
		return getMethod;
	}

	public void setGetMethod(GetMethod getMethod) {
		this.getMethod = getMethod;
	}

	public PostMethod getPostMethod() {
		return postMethod;
	}

	public void setPostMethod(PostMethod postMethod) {
		this.postMethod = postMethod;
	}

	@XMLLogging
	public InputStream getContentStream(String url) {

		try {
			HttpClient client = new HttpClient();
			setGetMethod(new GetMethod(url));
			client.executeMethod(getGetMethod());
			return getGetMethod().getResponseBodyAsStream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private boolean gotoStartTagContent(XMLStreamReader feedReader, String tagName) 
			throws Exception {
		while (feedReader.hasNext()) {
			feedReader.next();
			if (feedReader.getEventType() == XMLStreamReader.START_ELEMENT) {
				if (feedReader.getLocalName().equalsIgnoreCase(tagName)) {
					return true;
				}
			}
		}
		return false;
	}

	private void gotoEndTagContent(XMLStreamReader feedReader, String tagName)
			throws Exception {
		while (feedReader.hasNext()) {
			feedReader.next();
			if (feedReader.getEventType() == XMLStreamReader.END_ELEMENT) {
				if (feedReader.getLocalName().equalsIgnoreCase(tagName)) {
					return;
				}
			}
		}
	}
	
	private String getStartTagContent(XMLStreamReader feedReader, String tagName) 
			throws Exception {
		while (feedReader.hasNext()) {
			feedReader.next();
			if (feedReader.getEventType() == XMLStreamReader.START_ELEMENT) {
				if (feedReader.getLocalName().equalsIgnoreCase(tagName)) {
					return feedReader.getElementText();
				}
			}
		}
		return null;
	}

	
	private Date parseDate (String xmlDate) throws Exception {
		DateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
		if (xmlDate != null) {
			return format.parse(xmlDate);
		} else {
			return new Date();
		}
	}
	
	public ContentSource getContents (String url) throws Exception {
		ContentSource result = new ContentSource();
		result.setContents(new ArrayList<Content>());
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader feedReader = null;
		try {
			InputStream contentStream = getContentStream(url);
			feedReader = factory.createXMLStreamReader(contentStream);
			
			gotoStartTagContent(feedReader, "channel");
			result.setTitle(getStartTagContent(feedReader, "title"));
			result.setLink(getStartTagContent(feedReader, "link"));
			result.setImageUrl(getStartTagContent(feedReader, "url"));
			while (gotoStartTagContent(feedReader, "item")) {
				Content tmpContent = new Content();
				
				String title = getStartTagContent(feedReader, "title");
				tmpContent.setSummary(title);
				tmpContent.setTitle(title);
				tmpContent.setDetailUrl(getStartTagContent(feedReader, "link"));
				tmpContent.setPubDate(parseDate(getStartTagContent(feedReader, "pubDate")));
				String description = getStartTagContent(feedReader, "description");
				tmpContent.setPictureUrl(description);
				tmpContent.setSummary(getStartTagContent(feedReader, "title"));
				tmpContent.setTitle(getStartTagContent(feedReader, "title"));
				tmpContent.setType(ContentType.RSS);
				tmpContent.setProvider(result);
				result.getContents().add(tmpContent);
				gotoEndTagContent(feedReader, "item");
			}
		} finally {
			if (feedReader != null) {
				feedReader.close();
			}
		}
		return result;
		
	}

	public List<String> getFeedDescriptionList(InputStream content)
			throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader feedReader = factory.createXMLStreamReader(content);
		List<String> descriptionList = new ArrayList<String>();
		try {
			while (feedReader.hasNext()) {
				feedReader.next();
				if (feedReader.getEventType() == XMLStreamReader.START_ELEMENT) {
					if (feedReader.getLocalName().equalsIgnoreCase("item")) {
						while (feedReader.hasNext()) {
							if (feedReader.next() == XMLStreamReader.START_ELEMENT)
								if (feedReader.getLocalName().equalsIgnoreCase(
										"description")) {
									descriptionList.add(feedReader
											.getElementText());
								}
						}
					}
				}
			}
		} finally {
			feedReader.close();
		}
		return descriptionList;

	}

	public String getConcatDescriptions(String url) throws Exception {
		List<String> descriptions = getFeedDescriptionList(getContentStream(url));
		StringBuilder result = new StringBuilder();
		for (String tmpDesc : descriptions) {
			result.append(tmpDesc);
		}
		return result.toString();
	}

	public NodeList getTargetNodeListByXPath(InputStream input,
			String xPathExpression) throws Exception {
		XPathFactory xpFactory = XPathFactory.newInstance();
		XPath xp = xpFactory.newXPath();
		InputSource source = new InputSource(input);
		NodeList result = (NodeList) xp.evaluate(xPathExpression, source,
				XPathConstants.NODESET);
		return result;
	}

	public String getContent(InputStream input, String xPathExpression)
			throws Exception {
		NodeList nodeList = getTargetNodeListByXPath(input, xPathExpression);
		if (nodeList != null) {
			return nodeList.item(0).getTextContent();
		}
		return null;
	}

	public List<String> getContentList(InputStream input, String xPathExpression)
			throws Exception {
		List<String> result = new ArrayList<String>();
		NodeList nodeList = getTargetNodeListByXPath(input, xPathExpression);
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				result.add(nodeList.item(i).getTextContent());
			}
		}
		return result;
	}

	public String getContentWithPath(String url, String xPathExpression)
			throws Exception {
		return getContent(getContentStream(url), xPathExpression);
	}

	public List<String> getContentListWithPath(String url,
			String xPathExpression) throws Exception {
		return getContentList(getContentStream(url), xPathExpression);
	}

	public JSONObject parseJSON(InputStream input) throws Exception {
		String response = IOUtils.toString(input);
		JSONObject json = (JSONObject) JSONSerializer.toJSON(response);
		return json;
	}

	public JSONObject parseJSON(String url) throws Exception {
		return parseJSON(getContentStream(url));
	}

}
