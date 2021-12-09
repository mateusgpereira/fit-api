package io.pwii.service;

import io.pwii.entity.Exercise;
import io.pwii.model.PageModel;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.model.request.ExerciseUpdateRequestModel;

public interface ExerciseService {

  Exercise create(ExerciseRequestModel exercise);

  PageModel<Exercise> list(int page, int limit);

  Exercise update(Long exerciseId, ExerciseUpdateRequestModel exercise);

  void delete(Long exerciseId);

  Exercise getById(Long exerciseId);
  
}
