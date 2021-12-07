package io.pwii.resource.impl;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.pwii.entity.Athlete;
import io.pwii.mapper.AthleteMapper;
import io.pwii.model.AthleteRest;
import io.pwii.resource.AthleteResource;
import io.pwii.service.AthleteService;

@Path("/v1/athletes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AthleteResourceImpl implements AthleteResource {

  @Inject
  AthleteService athleteService;

  @Inject
  AthleteMapper athleteMapper;


  @POST
  @Transactional
  @Override
  public Response createAthlete(@Valid AthleteRest model) {
    Athlete createdEntity = athleteService.createAthlete(model);
    AthleteRest createdRest = athleteMapper.toRest(createdEntity);
    createdRest.setInstructorIdFromEntity(createdEntity.getInstructor());
    return Response.status(Response.Status.CREATED).entity(createdRest).build();
  }

}
