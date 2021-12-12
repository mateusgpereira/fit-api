package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.request.GymClassRequestModel;

public interface GymClassResource {

  Response createGymClass(@Valid GymClassRequestModel gymClass);

  Response list(int page, int limit);
  
}
