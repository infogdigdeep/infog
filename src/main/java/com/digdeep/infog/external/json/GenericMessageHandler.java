package com.digdeep.infog.external.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GenericMessageHandler implements MessageBodyWriter<Object>, MessageBodyReader<Object>{
	
	@Context
	private Providers providers;

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException, WebApplicationException {
		try {
			Unmarshaller unmarshaller = getJAXBContext(getClassType(genericType), mediaType).createUnmarshaller();
			//unmarshaller.setProperty("eclipselink.media-type", mediaType.toString());
            //unmarshaller.setProperty("eclipselink.json.include-root", false);
			return unmarshaller.unmarshal(new StreamSource(entityStream));
		} catch (Exception ex){
			throw new WebApplicationException(ex);
		}
	}

	@Override
	public long getSize(Object obj, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2,
			MediaType arg3) {
		return true;
	}

	@Override
	public void writeTo(Object obj, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream outputStream)
			throws IOException, WebApplicationException {
		try {
			Marshaller marshaller = getJAXBContext(getClassType(genericType), mediaType).createMarshaller();
            //marshaller.setProperty("eclipselink.media-type", mediaType.toString());
            //marshaller.setProperty("eclipselink.json.include-root", false);
			marshaller.marshal(obj, outputStream);
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}
		
	}

	private JAXBContext getJAXBContext(Class<?> type, MediaType mediaType) throws JAXBException {
		ContextResolver<?> resolver = providers.getContextResolver(type, mediaType);
		if (resolver != null) {
			JAXBContext result = (JAXBContext) resolver.getContext(type);
			if (result != null) {
				return result;
			}
		}
		return JAXBContext.newInstance(type);
	}
	
	private Class<?> getClassType (Type genericType) {
		if (genericType instanceof Class) {
			return (Class<?>) genericType;
		} else if (genericType instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
		}
		return null;
	}
}
