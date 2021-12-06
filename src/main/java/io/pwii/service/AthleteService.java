package io.pwii.service;

import io.pwii.entity.Athlete;
import io.pwii.model.AthleteRest;

public interface AthleteService {

  Athlete createAthlete(AthleteRest athlete);
  
}
