package io.pwii.service;

import io.pwii.entity.Athlete;
import io.pwii.model.PageModel;
import io.pwii.model.request.AthleteRequestModel;
import io.pwii.model.request.AthleteUpdateRequest;

public interface AthleteService {

  Athlete create(AthleteRequestModel athlete);

  PageModel<Athlete> list(int page, int limit);

  Athlete update(Long athleteId, AthleteUpdateRequest athlete);

  void delete(Long athleteId);

  Athlete getById(Long athleteId);
}
