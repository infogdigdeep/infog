package com.digdeep.infog.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GenericExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception ex) {
		return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
	}
	
	

}
