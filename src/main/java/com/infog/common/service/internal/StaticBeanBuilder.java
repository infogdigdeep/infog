package com.infog.common.service.internal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class StaticBeanBuilder implements ApplicationContextAware {

	private ApplicationContext currentCxt;
	
	public void registerSingleton (String name, Object beanClassInstance, ApplicationContext appCxt) {
		GenericApplicationContext staticCxt = new StaticApplicationContext();
		staticCxt.getBeanFactory().registerSingleton(name, beanClassInstance);
		staticCxt.refresh();
		
	}
	
	public ApplicationContext getWebApplicationContext(HttpSession session) {
		return WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	}

	public ApplicationContext getCurrentContext() {
		return currentCxt;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		currentCxt = context;
	}
}
