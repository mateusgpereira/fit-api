package io.pwii.service;

import io.pwii.entity.Athlete;
import io.pwii.model.AthleteRest;
import io.pwii.model.AthleteUpdateRequest;
import io.pwii.model.PageModel;

public interface AthleteService {

  Athlete create(AthleteRest athlete);

  PageModel<Athlete> list(int page, int limit);

  Athlete update(Long athleteId, AthleteUpdateRequest athlete);
  
}
