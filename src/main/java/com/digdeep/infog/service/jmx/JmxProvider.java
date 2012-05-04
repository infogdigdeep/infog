package com.digdeep.infog.service.jmx;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxProvider {
	
	public MBeanServerConnection getConnection () throws Exception {
		String host = "localhost";
		String port = "1090";
        String url = System.getProperty("jmx.service.url", 
                "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi");
        
        JMXServiceURL serviceUrl = new JMXServiceURL(url);
        JMXConnector connector = JMXConnectorFactory.connect(serviceUrl);
        return connector.getMBeanServerConnection();    
	}
}
