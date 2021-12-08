package io.pwii.exception;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestHandler implements ExceptionMapper<BadRequestException> {

  @Override
  public Response toResponse(BadRequestException exception) {
    return Response
        .status(Response.Status.BAD_REQUEST)
        .entity(new ErrorMessage(exception.getMessage()))
        .build();
  }

}
