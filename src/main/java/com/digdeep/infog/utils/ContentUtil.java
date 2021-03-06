package com.digdeep.infog.utils;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.digdeep.infog.model.Content;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.qualifiers.XMLLogging;

public class ContentUtil {

	private Logger logger = LoggerFactory.getLogger(ContentUtil.class);

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
			String response = getGetMethod().getResponseBodyAsString();
			return getGetMethod().getResponseBodyAsStream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private boolean gotoStartTagContent(XMLStreamReader feedReader,
			String tagName) throws Exception {
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

	private String getXMLStreamStr(InputStream input) {
		try {
			StreamSource source = new StreamSource(input);
			StreamResult result = new StreamResult(new StringWriter());
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer tf = factory.newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
					"2");
			tf.transform(source, result);
			return result.getWriter().toString();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
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

	private Date parseDate(String xmlDate) throws Exception {
		DateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
		if (xmlDate != null) {
			return format.parse(xmlDate);
		} else {
			return new Date();
		}
	}

	private Date parseXmlDate(String xmlDate) throws Exception {
		Calendar cal = DatatypeConverter.parseDate(xmlDate);
		return cal.getTime();
	}

	private void getContent(XMLStreamReader feedReader, List<Content> contents)
			throws Exception {
		Content result = null;
		boolean isAtFirstItem = true;
		while (feedReader.hasNext()) {
			if (isAtFirstItem) {
				isAtFirstItem = false;
			} else {
				feedReader.next();
			}
			if (feedReader.getEventType() == XMLStreamReader.START_ELEMENT) {
				if (feedReader.getLocalName().equalsIgnoreCase("item")
						|| feedReader.getLocalName().equalsIgnoreCase("entry")) {
					result = new Content();
					while (feedReader.hasNext()) {
						feedReader.next();
						if (feedReader.getEventType() == XMLStreamReader.START_ELEMENT) {
							String elementText = feedReader.getElementText();
							if (feedReader.getLocalName().equalsIgnoreCase(
									"pubDate")) {
								result.setPubDate(parseDate(elementText));
							} else if (feedReader.getLocalName()
									.equalsIgnoreCase("published")) {
								result.setPubDate(parseXmlDate(elementText));
							} else if (feedReader.getLocalName()
									.equalsIgnoreCase("title")) {
								result.setTitle(elementText);
								if (result.getSummary() == null) {
									result.setSummary(elementText);
								}
							} else if (feedReader.getLocalName()
									.equalsIgnoreCase("link")) {
								result.setDetailUrl(elementText);
							} else if (feedReader.getLocalName()
									.equalsIgnoreCase("description")
									|| feedReader.getLocalName()
											.equalsIgnoreCase("content")) {
								result.setSummary(elementText);
							}
						} else if (feedReader.getEventType() == XMLStreamReader.END_ELEMENT) {
							if (feedReader.getLocalName().equalsIgnoreCase(
									"item")) {
								contents.add(result);
								break;
							}
						}

					}
				}
			}

		}
	}

	public ContentSource getContents(String url) throws Exception {
		ContentSource result = new ContentSource();
		result.setContents(new ArrayList<Content>());
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader feedReader = null;
		try {
			InputStream contentStream = getContentStream(url);
			String contentStr = getXMLStreamStr(contentStream);
			feedReader = factory.createXMLStreamReader(new StringReader(
					contentStr));
			ContentType contentType = ContentType.RSS;

			boolean goNext = true;
			while (feedReader.hasNext()) {
				if (goNext) {
					feedReader.next();
				} else {
					goNext = true;
				}
				if (feedReader.getEventType() == XMLStreamReader.START_ELEMENT) {
					if (feedReader.getLocalName().equalsIgnoreCase("item")
							|| feedReader.getLocalName().equalsIgnoreCase(
									"entry")) {
						break;
					} else if (feedReader.getLocalName().equalsIgnoreCase(
							"feed")) {
						contentType = ContentType.ATOM;
					} else if (feedReader.getLocalName().equalsIgnoreCase(
							"title")) {
						result.setTitle(feedReader.getElementText());
					} else if (feedReader.getLocalName().equalsIgnoreCase(
							"link")) {
						result.setLink(feedReader.getElementText());
					} else if (feedReader.getLocalName().equalsIgnoreCase(
							"image")) {
						if (feedReader.hasNext()) {
							feedReader.next();
							if (feedReader.getEventType() == XMLStreamReader.START_ELEMENT
									&& feedReader.getLocalName()
											.equalsIgnoreCase("url")) {
								result.setImageUrl(feedReader.getElementText());
							} else {
								goNext = false;
							}
						}
					}
				}
			}
			getContent(feedReader, result.getContents());
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
