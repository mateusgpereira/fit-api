package io.pwii.exception;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class RuntimeExceptionHandler implements ExceptionMapper<RuntimeException> {

  @Inject
  Logger log;

  @Override
  public Response toResponse(RuntimeException exception) {
    return Response
        .status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(new ErrorMessage(exception.getMessage()))
        .build();
  }

}
