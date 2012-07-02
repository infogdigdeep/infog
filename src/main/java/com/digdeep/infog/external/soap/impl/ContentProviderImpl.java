package com.digdeep.infog.external.soap.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.digdeep.infog.beans.ContentProviderBean;
import com.digdeep.infog.external.soap.ContentProvider;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.input.ContentRequestInput;

@WebService(serviceName = "ContentProvider", targetNamespace = "http://infog.digdeep.com", endpointInterface = "com.digdeep.infog.external.soap.ContentProvider")
public class ContentProviderImpl implements ContentProvider {

	private ContentProviderBean contentProviderBean;

	public ContentProviderBean getContentProviderBean() {
		return contentProviderBean;
	}

	@Autowired
	public void setContentProviderBean(ContentProviderBean contentProviderBean) {
		this.contentProviderBean = contentProviderBean;
	}

	@Override
	public List<ContentSource> getInfo(ContentRequestInput input)
			throws Exception {
		return getContentProviderBean().getInfo(input);
	}

}
