package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.model.request.ExerciseUpdateRequestModel;

public interface ExerciseResource {

  Response createExercise(@Valid ExerciseRequestModel exercise);

  Response listExercises(int page, int limit);

  Response updateExercise(Long exerciseId, @Valid ExerciseUpdateRequestModel exercise);
  
}
