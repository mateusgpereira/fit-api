package io.pwii.exception;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DuplicateItemException implements ExceptionMapper<PersistenceException> {

  @Override
  public Response toResponse(PersistenceException exception) {
    System.out.println(exception.getClass());
    return Response
      .status(Response.Status.BAD_REQUEST)
      .entity(new ErrorMessage(exception.getMessage()))
      .build();
  }
  
}
