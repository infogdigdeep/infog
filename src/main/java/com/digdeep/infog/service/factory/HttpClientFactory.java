package com.digdeep.infog.service.factory;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientFactory {

	
	@Produces
	public GetMethod getGetMethod() {
		return new GetMethod(); 
	}
	
	@Produces
	public PostMethod getPostMethod() {
		return new PostMethod();
	}
	
	public void cleanGet (@Disposes GetMethod getMethod) {
		if (getMethod != null) {
			getMethod.releaseConnection();
		}
	}

	public void cleanPost (@Disposes PostMethod postMethod) {
		if (postMethod != null) {
			postMethod.releaseConnection();
		}
	}

}
