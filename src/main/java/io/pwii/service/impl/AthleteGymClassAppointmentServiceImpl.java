package io.pwii.service.impl;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import org.eclipse.microprofile.jwt.JsonWebToken;
import io.pwii.entity.Athlete;
import io.pwii.entity.GymClass;
import io.pwii.repository.AthleteRepository;
import io.pwii.repository.GymClassRepository;
import io.pwii.service.AthleteGymClassAppointmentService;

@ApplicationScoped
public class AthleteGymClassAppointmentServiceImpl implements AthleteGymClassAppointmentService {

  @Inject
  private AthleteRepository athleteRepository;

  @Inject
  private GymClassRepository gymClassRepository;

  @Transactional
  @Override
  public void create(JsonWebToken jwt, Long athleteId, Long gymClassId) {
    Athlete athlete = this.getAthleteByIdAndToken(jwt, athleteId);

    Optional<GymClass> optionalGymClass = gymClassRepository.findByIdOptional(gymClassId);
    if (optionalGymClass.isEmpty()) {
      throw new BadRequestException("Invalid GymClass");
    }

    GymClass gymClass = optionalGymClass.get();
    gymClass.addToAthletes(athlete);
    gymClassRepository.persist(gymClass);
  }

  @Transactional
  @Override
  public void delete(JsonWebToken jwt, Long athleteId, Long gymClassId) {
    Athlete athlete = this.getAthleteByIdAndToken(jwt, athleteId);

    Optional<GymClass> optionalGymClasse = gymClassRepository.findByIdOptional(gymClassId);
    if (optionalGymClasse.isEmpty()) {
      throw new BadRequestException("Invalid GymClass");
    }

    GymClass gymClass = optionalGymClasse.get();
    gymClass.removeFromAthlete(athlete);
    gymClassRepository.persist(gymClass);
  }

  private Athlete getAthleteByIdAndToken(JsonWebToken jwt, Long athleteId) {
    String userEmail = jwt.getClaim("upn");

    Optional<Athlete> optionalAthlete = athleteRepository.findByIdOptional(athleteId);
    if (optionalAthlete.isEmpty()) {
      throw new BadRequestException("Invalid Athlete");
    }

    Athlete athlete = optionalAthlete.get();
    if (!athlete.getEmail().equals(userEmail)) {
      throw new NotAuthorizedException("Not Authorized");
    }

    return athlete;
  }
  
}
