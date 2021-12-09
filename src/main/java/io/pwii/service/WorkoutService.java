package io.pwii.service;

import io.pwii.entity.Workout;
import io.pwii.model.request.WorkoutRequestModel;

public interface WorkoutService {

  Workout create(WorkoutRequestModel workout);
  
}
