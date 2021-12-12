package io.pwii.service;

import io.pwii.entity.Workout;
import io.pwii.model.PageModel;
import io.pwii.model.request.WorkoutRequestModel;
import io.pwii.model.request.WorkoutUpdateRequestModel;

public interface WorkoutService {

  Workout create(WorkoutRequestModel workout);

  PageModel<Workout> list(int page, int limit);

  Workout update(Long workoutId, WorkoutUpdateRequestModel workout);

  void delete(Long workoutId);

  Workout getById(Long workoutId);
  
}
