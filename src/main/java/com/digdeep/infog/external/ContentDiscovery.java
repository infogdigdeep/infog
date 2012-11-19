package com.digdeep.infog.external;

import java.util.List;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.digdeep.infog.beans.FeedDiscoveryBean;
import com.digdeep.infog.model.ContentInfo;

@Path("discover")
public class ContentDiscovery {
	
	@Autowired
	private FeedDiscoveryBean discoveryBean;

	public FeedDiscoveryBean getDiscoveryBean() {
		return discoveryBean;
	}

	public void setDiscoveryBean(FeedDiscoveryBean discoveryBean) {
		this.discoveryBean = discoveryBean;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("{query}")
	public List<ContentInfo> discover (@PathParam("query") @Encoded String query) {
		return getDiscoveryBean().findFeeds(query);
	}

}
