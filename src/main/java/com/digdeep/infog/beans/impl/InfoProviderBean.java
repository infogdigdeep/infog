package com.digdeep.infog.beans.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;


import com.digdeep.infog.exceptions.InvalidProviderException;
import com.digdeep.infog.model.Content;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.qualifiers.ContentConfig;
import com.digdeep.infog.service.InfoProvider;
import com.digdeep.infog.service.data.ContentInfoService;

@RequestScoped
public class InfoProviderBean {
	
	@Inject @Any
	private Instance<InfoProvider> providers;
	
	@EJB
	private ContentInfoService contentInfoService;
	

	public List<ContentSource> getAllByType(ContentRequestInput input) throws Exception {
		
		List<ContentInfo> contentList = contentInfoService.findAll();
		List<ContentSource> contents = new ArrayList<ContentSource>();
		for (ContentInfo tmpInfo : contentList) {
			input.setUrl(tmpInfo.getUrl());
			contents.add(getProvider(tmpInfo.getType()).get(input));
		}
		
		return contents;
	}
	
	public ContentSource get(ContentRequestInput input) throws Exception {
		return getProvider(ContentType.getType(input.getType())).get(input);
	}
	
	private InfoProvider getProvider (ContentType ct) throws Exception {
		ContentConfigQualifier ccQual = new ContentConfigQualifier(ct);
		Instance<InfoProvider> qualProvider = providers.select(ccQual);
		if (qualProvider.isUnsatisfied() || qualProvider.isAmbiguous()) {
			throw new InvalidProviderException(ct.getType());
		}
	
		return qualProvider.get();
	}
	
    class ContentConfigQualifier extends AnnotationLiteral<ContentConfig>
    implements ContentConfig {
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ContentType type;
    	
    	public ContentConfigQualifier(ContentType type) {
    		this.type = type;
    	}
    	
		@Override
		public ContentType contentType() {
			// TODO Auto-generated method stub
			return type;
		}
    	
    };

}
