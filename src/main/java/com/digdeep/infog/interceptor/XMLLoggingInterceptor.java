package com.digdeep.infog.interceptor;

import java.io.InputStream;
import java.io.StringWriter;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.digdeep.infog.qualifiers.XMLLogging;

@Interceptor
@XMLLogging
public class XMLLoggingInterceptor {
	private static Logger logger = LoggerFactory.getLogger(XMLLoggingInterceptor.class);
	
	@AroundInvoke
	public Object logInputStream (InvocationContext ctx) throws Exception {
		Object[] paramsList = ctx.getParameters();
		for (int i=0; i < paramsList.length; i++) {
			if (paramsList[i] instanceof InputStream) {
				logger.debug(prettyFormat((InputStream) paramsList[i], 1));
			}
		}
		Object result = ctx.proceed();
		if (result instanceof InputStream) {
			logger.debug(prettyFormat((InputStream) result, 1));
		}
		return result;
		
	}
	
	private String prettyFormat(InputStream input, int indent) {
		try {
			Source xmlInput = new StreamSource(input);
			StringWriter stringWriter = new StringWriter();
			StreamResult xmlOutput = new StreamResult(stringWriter);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			transformerFactory.setAttribute("indent-number", indent);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(xmlInput, xmlOutput);
			return xmlOutput.getWriter().toString();
		} catch (Exception e) {
			throw new RuntimeException(e); // simple exception handling, please
											// review it
		}
	}
}
