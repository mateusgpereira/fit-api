package io.pwii.service;

import io.pwii.entity.Exercise;
import io.pwii.model.PageModel;
import io.pwii.model.request.ExerciseRequestModel;

public interface ExerciseService {

  Exercise create(ExerciseRequestModel exercise);

  PageModel<Exercise> list(int page, int limit);
  
}
