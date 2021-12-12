package io.pwii.resource.impl;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.pwii.entity.GymClass;
import io.pwii.mapper.GymClassMapper;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.response.GymClassRestModel;
import io.pwii.resource.GymClassResource;
import io.pwii.service.GymClassService;

@Path("/v1/gymclasses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymClassResourceImpl implements GymClassResource {

  @Inject
  private GymClassService gymClassService;

  @Inject
  private GymClassMapper gymClassMapper;

  @POST
  @Override
  public Response createGymClass(@Valid GymClassRequestModel gymClass) {
    GymClass entity = gymClassService.create(gymClass);
    GymClassRestModel rest = gymClassMapper.toRest(entity);
    return Response.status(Response.Status.CREATED).entity(rest).build();
  }

}
