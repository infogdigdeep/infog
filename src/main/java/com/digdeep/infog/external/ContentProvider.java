package com.digdeep.infog.external;


import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.digdeep.infog.beans.InfoProviderBean;
import com.digdeep.infog.exceptions.InvalidProviderException;
import com.digdeep.infog.model.Content;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.input.ContentRequestInput;


@Path("content")
@RequestScoped
public class ContentProvider {

	@Inject
	private InfoProviderBean provider;
	
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public List<ContentSource> getInfo(ContentRequestInput input) throws InvalidProviderException, Exception {
		return provider.get(input);
	}

}
