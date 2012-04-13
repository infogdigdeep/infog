package com.digdeep.infog.exceptions;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;


public class FrontEndExceptionHandlerFactory extends ExceptionHandlerFactory {
    private ExceptionHandlerFactory parent;

    public FrontEndExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new FrontEndExceptionHandler(parent.getExceptionHandler());
	}


}
