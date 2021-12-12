package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.request.WorkoutRequestModel;
import io.pwii.model.request.WorkoutUpdateRequestModel;

public interface WorkoutResource {

  Response createWorkout(@Valid WorkoutRequestModel workout);

  Response listWorkouts(int page, int limit);

  Response updateWorkout(Long workoutId, WorkoutUpdateRequestModel workout);
  
}
