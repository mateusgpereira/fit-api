package io.pwii.service;

import io.pwii.entity.GymClass;
import io.pwii.model.PageModel;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.request.GymClassUpdateRequestModel;

public interface GymClassService {

  GymClass create(GymClassRequestModel gymClass);

  PageModel<GymClass> list(int page, int limit);

  GymClass update(Long gymClassId, GymClassUpdateRequestModel gymClass);

  void delete(Long gymClassId);

  GymClass getById(Long gymClassId);
  
}
