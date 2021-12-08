package io.pwii.resource.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.pwii.entity.Athlete;
import io.pwii.mapper.AthleteMapper;
import io.pwii.model.AthleteRest;
import io.pwii.model.AthleteUpdateRequest;
import io.pwii.model.PageModel;
import io.pwii.resource.AthleteResource;
import io.pwii.service.AthleteService;

@Path("/v1/athletes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AthleteResourceImpl implements AthleteResource {

  @Inject
  private AthleteService athleteService;

  @Inject
  private AthleteMapper athleteMapper;

  @POST
  @Transactional
  @Override
  public Response createAthlete(@Valid AthleteRest model) {
    Athlete createdEntity = athleteService.create(model);
    AthleteRest createdRest = athleteMapper.toRest(createdEntity);

    return Response.status(Response.Status.CREATED).entity(createdRest).build();
  }


  @GET
  @Override
  public Response listAthletes(
    @DefaultValue("0") @QueryParam("page") int page,
    @DefaultValue("25") @QueryParam("limit") int limit
  ) {
    PageModel<Athlete> entityPage = athleteService.list(page, limit);
    List<AthleteRest> listRest = entityPage.getContent().stream()
        .map(entity -> athleteMapper.toRest(entity))
        .collect(Collectors.toList());

    PageModel<AthleteRest> pageRest = PageModel.mapPage(entityPage, listRest);

    return Response.ok(pageRest).build();
  }


  @PUT
  @Path("/{athleteId}")
  @Override
  public Response updateAthlete(@PathParam("athleteId") Long athleteId, @Valid AthleteUpdateRequest model) {
    Athlete entity = athleteService.update(athleteId, model);
    AthleteRest rest = athleteMapper.toRest(entity);
    return Response.ok(rest).build();
  }


  @DELETE
  @Path("/{athleteId}")
  @Override
  public Response deleteAthlete(@PathParam("athleteId") Long athleteId) {
    athleteService.delete(athleteId);
    return Response.ok().build();
  }


  @GET
  @Path("/{athleteId}")
  @Override
  public Response getAthlete(@PathParam("athleteId") Long athleteId) {
    Athlete entity = athleteService.getById(athleteId);
    AthleteRest rest = athleteMapper.toRest(entity);
    return Response.ok(rest).build();
  }

}
