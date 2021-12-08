package io.pwii.exception;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundHandler implements ExceptionMapper<NotFoundException> {

  @Override
  public Response toResponse(NotFoundException ex) {
    return Response
        .status(Response.Status.NOT_FOUND)
        .entity(new ErrorMessage(ex.getMessage()))
        .build();
  }

}
