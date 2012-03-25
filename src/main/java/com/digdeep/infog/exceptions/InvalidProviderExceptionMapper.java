package com.digdeep.infog.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidProviderExceptionMapper implements ExceptionMapper<InvalidProviderException> {

	@Override
	public Response toResponse(InvalidProviderException ex) {
		return Response.status(Status.BAD_REQUEST).entity(ex.getMessage()).build();
	}

}
