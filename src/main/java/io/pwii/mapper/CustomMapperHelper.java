package io.pwii.mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import io.pwii.entity.Athlete;
import io.pwii.model.AthleteRest;

@ApplicationScoped
public class CustomMapperHelper {

  @Inject
  AthleteMapper athleteMapper;


  public AthleteRest fromEntityToRest(Athlete entity) {
    AthleteRest rest = athleteMapper.toRest(entity);
    rest.setInstructorId(entity.getInstructor().getId());
    return rest;
  }
  
}
