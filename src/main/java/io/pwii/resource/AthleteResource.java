package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.AthleteRest;

public interface AthleteResource {

  Response createAthlete(@Valid AthleteRest model);

  Response listAthletes(int page, int limit);

}
