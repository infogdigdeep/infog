package com.digdeep.infog.external;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.input.ConfigInput;
import com.digdeep.infog.service.data.ContentInfoService;
import com.digdeep.infog.service.data.UserService;


@Path("config")
@RequestScoped
public class ContentProvision {

	@EJB
	private ContentInfoService infoService;
	
	@EJB
	private UserService userService;
	
	@POST
	public void addConfig(ConfigInput cfg) {
		if (cfg.getContentInput() != null) {
			infoService.save(cfg.getContentInput());
		}
		if (cfg.getControlInput() != null) {
			userService.save(cfg.getControlInput());
		}
	}
	
	@GET
	public List<ContentInfo> getAllContentInfo() {
		return infoService.findAll();
	}
}
