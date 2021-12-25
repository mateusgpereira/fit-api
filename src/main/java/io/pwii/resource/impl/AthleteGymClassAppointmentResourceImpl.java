package io.pwii.resource.impl;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import io.pwii.resource.AthleteGymClassAppointmentResource;
import io.pwii.service.AthleteGymClassAppointmentService;

@Path("/v1/athletes/{athleteId}/gymclasses")
@RolesAllowed("ATHLETE")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AthleteGymClassAppointmentResourceImpl implements AthleteGymClassAppointmentResource {

  @Inject
  JsonWebToken jwt;

  @Inject
  private AthleteGymClassAppointmentService athleteGymClassAppointmentService;

  @POST
  @Path("/{gymClassId}")
  @Transactional
  @Override
  public Response createAppointmentGymClass(@Context SecurityContext context,
      @PathParam("athleteId") Long athleteId,
      @PathParam("gymClassId") Long gymClassId) {
    athleteGymClassAppointmentService.create(jwt, athleteId, gymClassId);
    return Response.ok().build();
  }

  @DELETE
  @Path("/{gymClassId}")
  @Transactional
  @Override
  public Response deleteAppointmentGymClass(@Context SecurityContext context,
      @PathParam("athleteId") Long athleteId,
      @PathParam("gymClassId") Long gymClassId) {
    athleteGymClassAppointmentService.delete(jwt, athleteId, gymClassId);
    return Response.ok().build();
  }

}
