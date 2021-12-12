package io.pwii.service;

import io.pwii.entity.GymClass;
import io.pwii.model.request.GymClassRequestModel;

public interface GymClassService {

  GymClass create(GymClassRequestModel gymClass);
  
}
