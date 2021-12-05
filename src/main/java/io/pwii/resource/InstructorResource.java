package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.InstructorRest;

public interface InstructorResource {

  Response createInstructor(@Valid InstructorRest model);
  
}
