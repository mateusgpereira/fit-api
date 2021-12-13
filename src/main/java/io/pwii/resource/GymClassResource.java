package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.request.GymClassUpdateRequestModel;

public interface GymClassResource {

  Response createGymClass(@Valid GymClassRequestModel gymClass);

  Response listGymClasses(int page, int limit);

  Response updateGymClass(Long gymClassId, GymClassUpdateRequestModel gymClass);

  Response deleteGymClass(Long gymClassId);

  Response getGymClass(Long gymClassId);
  
}
