package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.AthleteRest;
import io.pwii.model.AthleteUpdateRequest;

public interface AthleteResource {

  Response createAthlete(@Valid AthleteRest model);

  Response listAthletes(int page, int limit);

  Response updateAthlete(Long athleteId, @Valid AthleteUpdateRequest model);

}
