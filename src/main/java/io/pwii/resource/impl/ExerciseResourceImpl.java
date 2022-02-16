package io.pwii.resource.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
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
import io.pwii.entity.Exercise;
import io.pwii.entity.enums.WorkoutCategory;
import io.pwii.mapper.ExerciseMapper;
import io.pwii.model.PageModel;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.model.request.ExerciseUpdateRequestModel;
import io.pwii.model.response.ExerciseRestModel;
import io.pwii.resource.ExerciseResource;
import io.pwii.service.ExerciseService;

@Path("/v1/exercises")
@RolesAllowed("INSTRUCTOR")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExerciseResourceImpl implements ExerciseResource {

  @Inject
  private ExerciseService exerciseService;

  @Inject
  private ExerciseMapper exerciseMapper;

  @Inject
  JsonWebToken token;

  @POST
  @Override
  public Response createExercise(@Context SecurityContext ctx,
      @Valid ExerciseRequestModel exercise) {
    Exercise entity = exerciseService.create(exercise);
    ExerciseRestModel rest = exerciseMapper.toRest(entity);

    return Response.status(Response.Status.CREATED).entity(rest).build();
  }

  @GET
  @Override
  public Response listExercises(
      @Context SecurityContext ctx,
      @DefaultValue("0") @QueryParam("page") int page,
      @DefaultValue("25") @QueryParam("limit") int limit) {
    PageModel<Exercise> entityPage = exerciseService.list(page, limit);
    List<ExerciseRestModel> restList = entityPage.getContent().stream()
        .map(entity -> exerciseMapper.toRest(entity))
        .collect(Collectors.toList());

    PageModel<ExerciseRestModel> restPage = PageModel.mapPage(entityPage, restList);
    return Response.ok(restPage).build();
  }

  @PUT
  @Path("/{exerciseId}")
  @Override
  public Response updateExercise(
      @Context SecurityContext ctx,
      @PathParam("exerciseId") Long exerciseId,
      @Valid ExerciseUpdateRequestModel exercise) {
    Exercise updated = exerciseService.update(exerciseId, exercise);
    ExerciseRestModel rest = exerciseMapper.toRest(updated);
    return Response.ok(rest).build();
  }

  @DELETE
  @Path("/{exerciseId}")
  @Override
  public Response deleteExercise(@Context SecurityContext ctx, @PathParam("exerciseId") Long exerciseId) {
    exerciseService.delete(exerciseId);
    return Response.ok().build();
  }

  @GET
  @Path("/{exerciseId}")
  @Override
  public Response getExercise(@Context SecurityContext ctx, @PathParam("exerciseId") Long exerciseId) {
    Exercise entity = exerciseService.getById(exerciseId);
    ExerciseRestModel rest = exerciseMapper.toRest(entity);
    return Response.ok(rest).build();
  }

}
