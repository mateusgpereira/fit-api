package io.pwii.resource;

import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.request.GymClassUpdateRequestModel;
import io.pwii.model.request.UpdateRequestModel;

public interface GymClassResource {

  Response createGymClass(@Valid GymClassRequestModel gymClass);

  Response listGymClasses(int page, int limit);

  Response updateGymClass(Long gymClassId, GymClassUpdateRequestModel gymClass);

  Response deleteGymClass(Long gymClassId);

  Response getGymClass(Long gymClassId);

  Response updateGymClassAthletes(Long gymClassId, @Valid List<UpdateRequestModel<Long>> data);
  
}
