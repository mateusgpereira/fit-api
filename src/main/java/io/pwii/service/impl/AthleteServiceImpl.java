package io.pwii.service.impl;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import io.pwii.entity.Athlete;
import io.pwii.entity.Instructor;
import io.pwii.mapper.AthleteMapper;
import io.pwii.model.AthleteRest;
import io.pwii.repository.AthleteRepository;
import io.pwii.repository.InstructorRepository;
import io.pwii.service.AthleteService;
import io.quarkus.elytron.security.common.BcryptUtil;

@ApplicationScoped
public class AthleteServiceImpl implements AthleteService {

  @Inject
  AthleteRepository athleteRepository;

  @Inject
  InstructorRepository instructorRepository;

  @Inject
  AthleteMapper athleteMapper;

  @Override
  public Athlete createAthlete(AthleteRest athlete) {

    Athlete athleteEntity = athleteMapper.toEntity(athlete);
    athleteEntity.setPassword(BcryptUtil.bcryptHash(athlete.getPassword()));

    if (athlete.getInstructorId() != null) {
      Optional<Instructor> instructorOptional = instructorRepository.findByIdOptional(athlete.getInstructorId());

      if (instructorOptional.isPresent()) {
        athleteEntity.setInstructor(instructorOptional.get());
      }
    }

    athleteRepository.persist(athleteEntity);
    return athleteEntity;
  }
  
}
