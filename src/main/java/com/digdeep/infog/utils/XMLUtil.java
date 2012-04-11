package com.digdeep.infog.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;

public class XMLUtil {

	public XMLEventReader getStartElementReader (File inputFile) throws Exception {
		XMLInputFactory inpFactory = XMLInputFactory.newInstance();
		FileReader fReader = new FileReader(inputFile);
		XMLEventReader result = inpFactory.createXMLEventReader(fReader);
		EventFilter filter = new EventFilter() {
			
			@Override
			public boolean accept(XMLEvent event) {
				return event.isStartElement();
			}
		};
		
		return inpFactory.createFilteredReader(result, filter);
	}
	
	public Source dispatchXML (InputStream requestInputStream, String namespace, String serviceName, String portName) throws Exception {
		QName sName = new QName(namespace, serviceName);
		QName pName = new QName(namespace, portName);
		Service srv = Service.create(sName);
		/*
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage msg = factory.createMessage(null, requestInputStream);
		Dispatch<SOAPMessage> soapDispatch = srv.createDispatch(pName,
                SOAPMessage.class, Mode.MESSAGE);		
		SOAPMessage soapResp = soapDispatch.invoke(msg);
		String result = soapResp.getSOAPBody().getTextContent();
		*/
		
		StreamSource msg = new StreamSource(requestInputStream);
		Dispatch<Source> soapDispatch = srv.createDispatch(pName,
                Source.class, Mode.PAYLOAD);
		Source result = soapDispatch.invoke(msg);
		
		return result;

	}
	
	

}

