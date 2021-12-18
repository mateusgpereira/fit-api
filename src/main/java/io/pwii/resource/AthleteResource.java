package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.request.AthleteRequestModel;
import io.pwii.model.request.AthleteUpdateRequest;

public interface AthleteResource {

  Response createAthlete(@Valid AthleteRequestModel model);

  Response listAthletes(int page, int limit);

  Response updateAthlete(Long athleteId, @Valid AthleteUpdateRequest model);

  Response deleteAthlete(Long athleteId);

  Response getAthlete(Long athleteId);

}
