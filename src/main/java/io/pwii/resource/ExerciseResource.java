package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.request.ExerciseRequestModel;

public interface ExerciseResource {

  Response createExercise(@Valid ExerciseRequestModel exercise);

  Response listExercises(int page, int limit);
  
}
