package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.request.WorkoutRequestModel;

public interface WorkoutResource {

  Response createWorkout(@Valid WorkoutRequestModel workout);
  
}
