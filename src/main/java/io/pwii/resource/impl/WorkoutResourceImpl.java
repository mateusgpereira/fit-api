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
import io.pwii.entity.Workout;
import io.pwii.mapper.WorkoutMapper;
import io.pwii.model.PageModel;
import io.pwii.model.request.WorkoutExerciseUpdateRequestModel;
import io.pwii.model.request.WorkoutRequestModel;
import io.pwii.model.request.WorkoutUpdateRequestModel;
import io.pwii.model.response.WorkoutRestModel;
import io.pwii.resource.WorkoutResource;
import io.pwii.service.WorkoutService;

@Path("/v1/workouts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("INSTRUCTOR")
public class WorkoutResourceImpl implements WorkoutResource {

  @Inject
  private WorkoutService workoutService;

  @Inject
  private WorkoutMapper workoutMapper;

  @Inject
  JsonWebToken token;

  @POST
  @Override
  public Response createWorkout(@Context SecurityContext ctx, @Valid WorkoutRequestModel workout) {
    Workout entity = workoutService.create(workout);
    WorkoutRestModel rest = workoutMapper.toRest(entity);
    return Response.ok(rest).build();
  }

  @GET
  @Override
  public Response listWorkouts(
      @Context SecurityContext ctx,
      @DefaultValue("0") @QueryParam("page") int page,
      @DefaultValue("25") @QueryParam("limit") int limit) {
    PageModel<Workout> pageEntity = workoutService.list(page, limit);
    List<WorkoutRestModel> listRest = pageEntity.getContent()
        .stream()
        .map(entity -> workoutMapper.toRest(entity))
        .collect(Collectors.toList());

    PageModel<WorkoutRestModel> pageRest = PageModel.mapPage(pageEntity, listRest);
    return Response.ok(pageRest).build();
  }

  @PUT
  @Path("/{workoutId}")
  @Transactional
  @Override
  public Response updateWorkout(
      @Context SecurityContext ctx,
      @PathParam("workoutId") Long workoutId,
      WorkoutUpdateRequestModel workout) {
    Workout updated = workoutService.update(workoutId, workout);
    WorkoutRestModel rest = workoutMapper.toRest(updated);
    return Response.ok(rest).build();
  }

  @DELETE
  @Path("/{workoutId}")
  @Override
  public Response deleteWorkouts(@Context SecurityContext ctx,
      @PathParam("workoutId") Long workoutId) {
    workoutService.delete(workoutId);
    return Response.ok().build();
  }

  @GET
  @Path("/{workoutId}")
  @Override
  public Response getWorkout(@Context SecurityContext ctx, @PathParam("workoutId") Long workoutId) {
    Workout entity = workoutService.getById(workoutId);
    WorkoutRestModel rest = workoutMapper.toRest(entity);
    return Response.ok(rest).build();
  }

  @PATCH
  @Path("/{workoutId}/exercises")
  @Transactional
  @Override
  public Response updateWorkoutExercises(
      @Context SecurityContext ctx,
      @PathParam("workoutId") Long workoutId,
      @Valid List<WorkoutExerciseUpdateRequestModel> data) {
    Workout entity = workoutService.updateExercises(workoutId, data);
    WorkoutRestModel rest = workoutMapper.toRest(entity);

    return Response.ok(rest).build();
  }

  @DELETE
  @PATCH
  @Path("/{workoutId}/exercises/{exerciseId}")
  @Transactional
  @Override
  public Response removeExerciseFromWorkout(
      @Context SecurityContext ctx,
      @PathParam("workoutId") Long workoutId,
      @PathParam("exerciseId") Long exerciseId) {
    Workout entity = workoutService.removeExercise(workoutId, exerciseId);
    WorkoutRestModel rest = workoutMapper.toRest(entity);

    return Response.ok(rest).build();
  }

}
