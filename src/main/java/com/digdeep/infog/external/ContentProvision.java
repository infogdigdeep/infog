package com.digdeep.infog.external;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.input.ContentProvisionInput;
import com.digdeep.infog.service.data.ContentInfoService;


@Path("contentinfo")
@RequestScoped
public class ContentProvision {

	@EJB
	private ContentInfoService infoService;
	
	@PUT
	public void addContentInfo(ContentProvisionInput info) {
		infoService.save(info);
	}
}
