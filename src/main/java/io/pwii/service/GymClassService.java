package io.pwii.service;

import java.util.List;
import io.pwii.entity.Athlete;
import io.pwii.entity.GymClass;
import io.pwii.model.PageModel;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.request.GymClassUpdateRequestModel;
import io.pwii.model.request.UpdateRequestModel;

public interface GymClassService {

  GymClass create(GymClassRequestModel gymClass);

  PageModel<GymClass> list(int page, int limit);

  GymClass update(Long gymClassId, GymClassUpdateRequestModel gymClass);

  void delete(Long gymClassId);

  GymClass getById(Long gymClassId);

  GymClass updateAthletes(Long gymClassId, List<UpdateRequestModel<Long>> data);
  
}
