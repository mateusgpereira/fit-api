package io.pwii.resource;

import javax.ws.rs.core.Response;
import io.pwii.model.InstructorRest;

public interface InstructorResource {

  Response createInstructor(InstructorRest model);
  
}
