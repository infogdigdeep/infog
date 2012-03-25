package com.digdeep.infog.external;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.digdeep.infog.beans.InfoProviderBean;
import com.digdeep.infog.exceptions.InvalidProviderException;
import com.digdeep.infog.model.input.ContentRequestInput;


@Path("infog")
@RequestScoped
public class ContentProvider {

	@Inject
	private InfoProviderBean provider;
	
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public String getInfo(ContentRequestInput input) throws InvalidProviderException, Exception {
		return provider.get(input);
	}

}
