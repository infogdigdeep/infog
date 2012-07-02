package com.digdeep.infog.external;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.digdeep.infog.beans.impl.ContentProviderBeanImpl;
import com.digdeep.infog.exceptions.InvalidProviderException;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.input.ContentRequestInput;


@Path("content")
public class ContentProvider {

	private ContentProviderBeanImpl provider;

	public ContentProviderBeanImpl getProvider() {
		return provider;
	}

	public void setProvider(ContentProviderBeanImpl provider) {
		this.provider = provider;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public List<ContentSource> getInfo(ContentRequestInput input) throws InvalidProviderException, Exception {
		return provider.getInfo(input);
	}
	
}
