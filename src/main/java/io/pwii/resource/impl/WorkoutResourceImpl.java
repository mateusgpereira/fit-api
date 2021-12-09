package io.pwii.resource.impl;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.pwii.entity.Workout;
import io.pwii.mapper.WorkoutMapper;
import io.pwii.model.request.WorkoutRequestModel;
import io.pwii.model.response.WorkoutRestModel;
import io.pwii.resource.WorkoutResource;
import io.pwii.service.WorkoutService;

@Path("/v1/workouts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkoutResourceImpl implements WorkoutResource {

  @Inject
  private WorkoutService workoutService;

  @Inject
  private WorkoutMapper workoutMapper;

  @POST
  @Override
  public Response createWorkout(@Valid WorkoutRequestModel workout) {
    Workout entity = workoutService.create(workout);
    WorkoutRestModel rest = workoutMapper.toRest(entity);
    return Response.ok(rest).build();
  }
  
}
