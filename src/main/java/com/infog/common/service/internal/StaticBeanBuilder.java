package com.infog.common.service.internal;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class StaticBeanBuilder {

	public void registerSingleton (String name, Object beanClassInstance, ApplicationContext appCxt) {
		GenericApplicationContext staticCxt = new StaticApplicationContext();
		staticCxt.getBeanFactory().registerSingleton(name, beanClassInstance);
		staticCxt.refresh();
		
	}
	
	public ApplicationContext getWebApplicationContext(HttpSession session) {
		return WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	}
}
