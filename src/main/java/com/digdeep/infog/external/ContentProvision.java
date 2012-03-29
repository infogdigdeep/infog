package com.digdeep.infog.external;

import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.input.ContentProvisionInput;
import com.digdeep.infog.service.data.ContentInfoService;


@Path("contentinfo")
public class ContentProvision {

	@Inject
	private ContentInfoService infoService;
	
	@PUT
	public void addContentInfo(ContentProvisionInput info) {
		infoService.save(info);
	}
}
