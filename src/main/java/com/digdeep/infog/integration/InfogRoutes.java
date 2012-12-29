package com.digdeep.infog.integration;

import org.apache.camel.builder.RouteBuilder;

public class InfogRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("jms:queue:feedrequest").to("bean:feedDiscovery:findFeeds");
	}

}
