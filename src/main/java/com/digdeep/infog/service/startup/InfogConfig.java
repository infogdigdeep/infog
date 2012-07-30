package com.digdeep.infog.service.startup;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.impl.osgi.tracker.BundleTrackerCustomizer;
import org.apache.camel.osgi.SpringCamelContextFactory;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.osgi.framework.BundleContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.osgi.context.BundleContextAware;

@Configuration
public class InfogConfig extends SingleRouteCamelConfiguration implements InitializingBean, BundleContextAware {

	private BundleContext context;
	
	@Override
	public void setBundleContext(BundleContext context) {
		this.context = context;
	}
	
	public BundleContext getBundleContext () {
		return this.context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected CamelContext createCamelContext() throws Exception {
		SpringCamelContextFactory factory = new SpringCamelContextFactory();
		factory.setApplicationContext(getApplicationContext());
		factory.setBundleContext(getBundleContext());
		SimpleRegistry registry = new SimpleRegistry();
		//registry.put("busName", eventBus);
		return factory.createContext();
	}

	
	@Override
	protected void setupCamelContext(CamelContext camelContext)
			throws Exception {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("vm://localhost.spring.javaconfig?marshal=false&broker.persistent=false&broker.useJmx=false");
        JmsComponent infogCoreJms = new JmsComponent();
        infogCoreJms.setConnectionFactory(connectionFactory);
        camelContext.addComponent("infogcore", infogCoreJms);
	}

	@Override
	public RouteBuilder route() {
		return new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
                // you can configure the route rule with Java DSL here

                // populate the message queue with some messages
                from("file:src/data?noop=true").
                        to("jms:test.MyQueue");

                from("jms:test.MyQueue").
                        to("file://target/test?noop=true");


            }
		
		};
	}
	
	
}
