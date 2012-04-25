package com.digdeep.infog.service.startup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digdeep.infog.service.data.ContentInfoService;
import com.digdeep.infog.service.data.UserService;

@Startup
@Singleton
public class InfogCore {
	private Logger logger = LoggerFactory.getLogger(InfogCore.class);
	
	@EJB
	private ContentInfoService infoService;
	
	@EJB
	private UserService userService;
	
	@PostConstruct
	public void init() {
		logger.info(">>>> Start Infog");
		//userService.storeStaticUserInfo();
		//infoService.storeStaticContentInfo();
		
		logger.info(">>>> Finish Stored Static Content");
	}
	
	
	@PreDestroy
	public void terminate() {		
		logger.info(">>>> Stop Infog");
	}
}
