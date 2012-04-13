package com.digdeep.infog.exceptions;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class FrontEndExceptionHandler extends ExceptionHandlerWrapper {

	public ExceptionHandler wrapped;
	
	public FrontEndExceptionHandler(ExceptionHandler parent) {
		this.wrapped = parent;
	}
	
	@Override
	public ExceptionQueuedEvent getHandledExceptionQueuedEvent() {
		// TODO Auto-generated method stub
		return super.getHandledExceptionQueuedEvent();
	}

	@Override
	public Iterable<ExceptionQueuedEvent> getHandledExceptionQueuedEvents() {
		// TODO Auto-generated method stub
		return super.getHandledExceptionQueuedEvents();
	}

	@Override
	public Throwable getRootCause(Throwable arg0) {
		// TODO Auto-generated method stub
		return super.getRootCause(arg0);
	}

	@Override
	public Iterable<ExceptionQueuedEvent> getUnhandledExceptionQueuedEvents() {
		// TODO Auto-generated method stub
		return super.getUnhandledExceptionQueuedEvents();
	}

	@Override
	public void handle() throws FacesException {
	    for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
	        ExceptionQueuedEvent event = i.next();
	        ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
	        Throwable t = context.getException();
	        if (t instanceof ViewExpiredException) {
	            ViewExpiredException vee = (ViewExpiredException) t;
	            FacesContext fc = FacesContext.getCurrentInstance();
	            Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
	            NavigationHandler nav =
	                    fc.getApplication().getNavigationHandler();
	            try {
	                // Push some stuff to the request scope for later use in the page
	                requestMap.put("currentViewId", vee.getViewId());
	                nav.handleNavigation(fc, null, "viewExpired");
	                fc.renderResponse();
	 
	            } finally {
	                i.remove();
	            }
	        }
	    }
	    // Let the parent handle all the remaining queued exception events.
	    getWrapped().handle();
		
	}


	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	public void setWrapped(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

}
