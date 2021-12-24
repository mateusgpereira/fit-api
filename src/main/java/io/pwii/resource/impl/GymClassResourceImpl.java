package io.pwii.resource.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import io.pwii.entity.GymClass;
import io.pwii.mapper.GymClassMapper;
import io.pwii.model.PageModel;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.request.GymClassUpdateRequestModel;
import io.pwii.model.request.UpdateRequestModel;
import io.pwii.model.response.GymClassRestModel;
import io.pwii.resource.GymClassResource;
import io.pwii.service.GymClassService;

@Path("/v1/gymclasses")
@RolesAllowed("INSTRUCTOR")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymClassResourceImpl implements GymClassResource {

  @Inject
  private GymClassService gymClassService;

  @Inject
  private GymClassMapper gymClassMapper;

  @Inject
  JsonWebToken token;

  @POST
  @Override
  public Response createGymClass(@Context SecurityContext ctx,
      @Valid GymClassRequestModel gymClass) {
    GymClass entity = gymClassService.create(gymClass);
    GymClassRestModel rest = gymClassMapper.toRest(entity);
    return Response.status(Response.Status.CREATED).entity(rest).build();
  }

  @GET
  @Override
  public Response listGymClasses(
      @Context SecurityContext ctx,
      @DefaultValue(value = "0") @QueryParam("page") int page,
      @DefaultValue(value = "25") @QueryParam("limit") int limit) {
    PageModel<GymClass> entityPage = gymClassService.list(page, limit);
    List<GymClassRestModel> listRest = entityPage.getContent()
        .stream()
        .map(entity -> gymClassMapper.toRest(entity))
        .collect(Collectors.toList());
    PageModel<GymClassRestModel> restPage = PageModel.mapPage(entityPage, listRest);
    return Response.ok(restPage).build();
  }

  @PUT
  @Path("/{gymClassId}")
  @Transactional
  @Override
  public Response updateGymClass(
      @Context SecurityContext ctx,
      @PathParam("gymClassId") Long gymClassId,
      GymClassUpdateRequestModel gymClass) {
    GymClass updatedEntity = gymClassService.update(gymClassId, gymClass);
    GymClassRestModel updatedRest = gymClassMapper.toRest(updatedEntity);
    return Response.ok(updatedRest).build();
  }

  @DELETE
  @Path("/{gymClassId}")
  @Override
  public Response deleteGymClass(@Context SecurityContext ctx,
      @PathParam("gymClassId") Long gymClassId) {
    gymClassService.delete(gymClassId);
    return Response.ok().build();
  }

  @GET
  @Path("/{gymClassId}")
  @Override
  public Response getGymClass(@Context SecurityContext ctx,
      @PathParam("gymClassId") Long gymClassId) {
    GymClass entity = gymClassService.getById(gymClassId);
    GymClassRestModel rest = gymClassMapper.toRest(entity);
    return Response.ok(rest).build();
  }

  @PATCH
  @Path("/{gymClassId}/athletes")
  @Transactional
  @Override
  public Response updateGymClassAthletes(@Context SecurityContext ctx,
      @PathParam("gymClassId") Long gymClassId,
      @Valid List<UpdateRequestModel<Long>> data) {
    GymClass entity = gymClassService.updateAthletes(gymClassId, data);
    GymClassRestModel rest = gymClassMapper.toRest(entity);
    return Response.ok(rest).build();
  }

  @PATCH
  @Path("/{gymClassId}/athletes/{athleteId}")
  @Transactional
  @Override
  public Response addAthleteToGymClass(@Context SecurityContext ctx,
      @PathParam("gymClassId") Long gymClassId,
      @PathParam("athleteId") Long athleteId) {
    GymClass entity = gymClassService.addAthlete(gymClassId, athleteId);
    GymClassRestModel rest = gymClassMapper.toRest(entity);
    return Response.ok(rest).build();
  }

  @DELETE
  @Path("/{gymClassId}/athletes/{athleteId}")
  @Transactional
  @Override
  public Response removeAthleteFromGymClass(@Context SecurityContext ctx,
      @PathParam("gymClassId") Long gymClassId,
      @PathParam("athleteId") Long athleteId) {
    GymClass entity = gymClassService.deleteAthlete(gymClassId, athleteId);
    GymClassRestModel rest = gymClassMapper.toRest(entity);
    return Response.ok(rest).build();
  }

}
