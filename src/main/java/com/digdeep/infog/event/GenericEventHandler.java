package com.digdeep.infog.event;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digdeep.infog.model.ContentSource;
import com.google.common.eventbus.Subscribe;


public class GenericEventHandler {
	private Logger log = LoggerFactory.getLogger(GenericEventHandler.class);
	
	@Subscribe
	public void handleEvent (GenericEvent event) {
		log.info("Event Fired");
		List<ContentSource> sources = event.getContents();
		for (ContentSource tmpSrc : sources) {
			log.info(tmpSrc.getTitle());
		}
	}
}
