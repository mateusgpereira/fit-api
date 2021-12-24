package io.pwii.resource.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import io.pwii.entity.Athlete;
import io.pwii.mapper.AthleteMapper;
import io.pwii.model.PageModel;
import io.pwii.model.request.AthleteRequestModel;
import io.pwii.model.request.AthleteUpdateRequest;
import io.pwii.model.response.AthleteRestModel;
import io.pwii.resource.AthleteResource;
import io.pwii.service.AthleteService;

@Path("/v1/athletes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("INSTRUCTOR")
public class AthleteResourceImpl implements AthleteResource {

  @Inject
  private AthleteService athleteService;

  @Inject
  private AthleteMapper athleteMapper;

  @Inject
  JsonWebToken token;

  @POST
  @Transactional
  @PermitAll
  @Override
  public Response createAthlete(@Valid AthleteRequestModel model) {
    Athlete createdEntity = athleteService.create(model);
    AthleteRestModel createdRest = athleteMapper.toRest(createdEntity);

    return Response.status(Response.Status.CREATED).entity(createdRest).build();
  }


  @GET
  @Override
  public Response listAthletes(
      @Context SecurityContext ctx,
      @DefaultValue("0") @QueryParam("page") int page,
      @DefaultValue("25") @QueryParam("limit") int limit) {
    PageModel<Athlete> entityPage = athleteService.list(page, limit);
    List<AthleteRestModel> listRest = entityPage.getContent().stream()
        .map(entity -> athleteMapper.toRest(entity))
        .collect(Collectors.toList());

    PageModel<AthleteRestModel> pageRest = PageModel.mapPage(entityPage, listRest);

    return Response.ok(pageRest).build();
  }


  @PUT
  @Path("/{athleteId}")
  @Override
  public Response updateAthlete(
      @Context SecurityContext ctx,
      @PathParam("athleteId") Long athleteId,
      @Valid AthleteUpdateRequest model) {
    Athlete entity = athleteService.update(athleteId, model);
    AthleteRestModel rest = athleteMapper.toRest(entity);
    return Response.ok(rest).build();
  }


  @DELETE
  @Path("/{athleteId}")
  @Override
  public Response deleteAthlete(@Context SecurityContext ctx,
      @PathParam("athleteId") Long athleteId) {
    athleteService.delete(athleteId);
    return Response.ok().build();
  }


  @GET
  @Path("/{athleteId}")
  @Override
  public Response getAthlete(@Context SecurityContext ctx, @PathParam("athleteId") Long athleteId) {
    Athlete entity = athleteService.getById(athleteId);
    AthleteRestModel rest = athleteMapper.toRest(entity);
    return Response.ok(rest).build();
  }

}
