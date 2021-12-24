package io.pwii.resource;

import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import io.pwii.model.request.WorkoutExerciseUpdateRequestModel;
import io.pwii.model.request.WorkoutRequestModel;
import io.pwii.model.request.WorkoutUpdateRequestModel;

public interface WorkoutResource {

  Response createWorkout(SecurityContext ctx, @Valid WorkoutRequestModel workout);

  Response listWorkouts(SecurityContext ctx, int page, int limit);

  Response updateWorkout(SecurityContext ctx, Long workoutId, WorkoutUpdateRequestModel workout);

  Response deleteWorkouts(SecurityContext ctx, Long workoutId);

  Response getWorkout(SecurityContext ctx, Long workoutId);

  Response updateWorkoutExercises(SecurityContext ctx, Long workoutId,
      @Valid List<WorkoutExerciseUpdateRequestModel> data);

  Response removeExerciseFromWorkout(SecurityContext ctx, Long workoutId, Long exerciseId);
}
