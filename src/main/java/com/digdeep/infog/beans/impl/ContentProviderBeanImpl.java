package com.digdeep.infog.beans.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

import com.digdeep.infog.beans.ContentProviderBeanLocal;
import com.digdeep.infog.beans.ContentProviderBeanRemote;
import com.digdeep.infog.exceptions.InvalidProviderException;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.input.ContentRequestInput;

@Stateless (mappedName="ejb/cpb")
@Remote({ContentProviderBeanRemote.class})
@Local({ContentProviderBeanLocal.class})
public class ContentProviderBeanImpl implements ContentProviderBeanLocal, ContentProviderBeanRemote {
	
	@Inject
	private InfoProviderBean provider;
	
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public List<ContentSource> getInfo(ContentRequestInput input) throws InvalidProviderException, Exception {
		return provider.getAllByType(input);
	}

}
