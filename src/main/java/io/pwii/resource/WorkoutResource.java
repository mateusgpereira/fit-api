package io.pwii.resource;

import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.request.UpdateRequestModel;
import io.pwii.model.request.WorkoutRequestModel;
import io.pwii.model.request.WorkoutUpdateRequestModel;

public interface WorkoutResource {

  Response createWorkout(@Valid WorkoutRequestModel workout);

  Response listWorkouts(int page, int limit);

  Response updateWorkout(Long workoutId, WorkoutUpdateRequestModel workout);

  Response deleteWorkouts(Long workoutId);

  Response getWorkout(Long workoutId);

  Response updateWorkoutExercises(Long workoutId, @Valid List<UpdateRequestModel<Long>> data);

  Response removeExerciseFromWorkout(Long workoutId, Long exerciseId);
}
