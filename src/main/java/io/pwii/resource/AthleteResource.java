package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import io.pwii.model.request.AthleteRequestModel;
import io.pwii.model.request.AthleteUpdateRequest;

public interface AthleteResource {

  Response createAthlete(@Valid AthleteRequestModel model);

  Response listAthletes(SecurityContext ctx, int page, int limit);

  Response updateAthlete(SecurityContext ctx, Long athleteId, @Valid AthleteUpdateRequest model);

  Response deleteAthlete(SecurityContext ctx, Long athleteId);

  Response getAthlete(SecurityContext ctx, Long athleteId);

}
