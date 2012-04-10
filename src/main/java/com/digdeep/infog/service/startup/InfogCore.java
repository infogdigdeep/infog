package com.digdeep.infog.service.startup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@Singleton
public class InfogCore {
	private Logger logger = LoggerFactory.getLogger(InfogCore.class);
	
	@PostConstruct
	public void init() {
		logger.info(">>>> Start Infog");
	}
	
	
	@PreDestroy
	public void terminate() {		
		logger.info(">>>> Stop Infog");
	}
}
