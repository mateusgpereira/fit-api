package io.pwii.resource;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

public interface AthleteGymClassAppointmentResource {

  Response createAppointmentGymClass(SecurityContext context, Long athleteId, Long gymClassId);

  Response deleteAppointmentGymClass(SecurityContext context, Long athleteId, Long gymClassId);

}
