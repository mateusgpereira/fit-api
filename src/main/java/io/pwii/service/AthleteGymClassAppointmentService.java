package io.pwii.service;

import org.eclipse.microprofile.jwt.JsonWebToken;

public interface AthleteGymClassAppointmentService {

  void create(JsonWebToken jwt, Long athleteId, Long gymClassId);
  
  void delete(JsonWebToken jwt, Long athleteId, Long gymClassId);
  
}
