package com.digdeep.infog.service.startup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digdeep.infog.event.GenericEventHandler;
import com.digdeep.infog.service.data.ContentInfoService;
import com.digdeep.infog.service.data.UserService;
import com.google.common.eventbus.EventBus;

@Startup
@Singleton
public class InfogCore {
	private Logger logger = LoggerFactory.getLogger(InfogCore.class);
	
	@EJB
	private ContentInfoService infoService;
	
	@EJB
	private UserService userService;
	
	private EventBus bus;
	
	@PostConstruct
	public void init() {
		logger.info(">>>> Start Infog");
		try {
		//userService.storeStaticUserInfo();
		//infoService.storeStaticContentInfo();
			bus = new EventBus();
			bus.register(new GenericEventHandler());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info(">>>> Finish Stored Static Content");
	}
	
	
	public EventBus getBus() {
		return bus;
	}


	public void setBus(EventBus bus) {
		this.bus = bus;
	}


	@PreDestroy
	public void terminate() {		
		logger.info(">>>> Stop Infog");
	}
}
