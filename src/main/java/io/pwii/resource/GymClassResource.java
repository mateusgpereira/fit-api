package io.pwii.resource;

import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.request.GymClassUpdateRequestModel;
import io.pwii.model.request.UpdateRequestModel;

public interface GymClassResource {

  Response createGymClass(SecurityContext ctx, @Valid GymClassRequestModel gymClass);

  Response listGymClasses(SecurityContext ctx, int page, int limit);

  Response updateGymClass(SecurityContext ctx, Long gymClassId, GymClassUpdateRequestModel gymClass);

  Response deleteGymClass(SecurityContext ctx, Long gymClassId);

  Response getGymClass(SecurityContext ctx, Long gymClassId);

  Response updateGymClassAthletes(SecurityContext ctx, Long gymClassId, @Valid List<UpdateRequestModel<Long>> data);

  Response addAthleteToGymClass(SecurityContext ctx, Long gymClassId, Long athleteId);

  Response removeAthleteFromGymClass(SecurityContext ctx, Long gymClassId, Long athleteId);
  
}
