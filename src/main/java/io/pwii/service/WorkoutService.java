package io.pwii.service;

import java.util.List;
import io.pwii.entity.Workout;
import io.pwii.model.PageModel;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.model.request.UpdateRequestModel;
import io.pwii.model.request.WorkoutExerciseUpdateRequestModel;
import io.pwii.model.request.WorkoutRequestModel;
import io.pwii.model.request.WorkoutUpdateRequestModel;

public interface WorkoutService {

  Workout create(WorkoutRequestModel workout);

  PageModel<Workout> list(int page, int limit);

  Workout update(Long workoutId, WorkoutUpdateRequestModel workout);

  void delete(Long workoutId);

  Workout getById(Long workoutId);

  Workout updateExercises(Long workoutId, List<WorkoutExerciseUpdateRequestModel> data);

  Workout removeExercise(Long workoutId, Long exerciseId);
  
}
