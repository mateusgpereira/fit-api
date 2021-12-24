package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.model.request.ExerciseUpdateRequestModel;

public interface ExerciseResource {

  Response createExercise(SecurityContext ctx, @Valid ExerciseRequestModel exercise);

  Response listExercises(SecurityContext ctx, int page, int limit);

  Response updateExercise(SecurityContext ctx, Long exerciseId, @Valid ExerciseUpdateRequestModel exercise);

  Response deleteExercise(SecurityContext ctx, Long exerciseId);

  Response getExercise(SecurityContext ctx, Long exerciseId);
  
}
