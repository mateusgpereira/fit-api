package io.pwii.resource.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
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
import io.pwii.entity.Instructor;
import io.pwii.mapper.InstructorMapper;
import io.pwii.model.InstructorUpdateRequest;
import io.pwii.model.PageModel;
import io.pwii.model.request.InstructorRequestModel;
import io.pwii.model.response.InstructorRestModel;
import io.pwii.resource.InstructorResource;
import io.pwii.service.InstructorService;

@Path("/v1/instructors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InstructorResourceImpl implements InstructorResource {

  @Inject
  private InstructorService instructorService;

  @Inject
  private InstructorMapper instructorMapper;

  @POST
  @Override
  public Response createInstructor(@Valid InstructorRequestModel model) {
    final Instructor created = instructorService.create(model);
    InstructorRestModel createdRest = instructorMapper.toRest(created);
    return Response.status(Response.Status.CREATED).entity(createdRest).build();
  }

  @GET
  @Override
  public Response listInstructors(
      @DefaultValue("0") @QueryParam("page") int page,
      @DefaultValue("25") @QueryParam("limit") int limit) {
    PageModel<Instructor> entityPage = instructorService.list(page, limit);
    List<InstructorRestModel> list = entityPage.getContent().stream()
        .map(entity -> instructorMapper.toRest(entity))
        .collect(Collectors.toList());

    PageModel<InstructorRestModel> restPage = PageModel.<InstructorRestModel>mapPage(entityPage, list);

    return Response.ok(restPage).build();
  }

  @PUT
  @Path("/{instructorId}")
  @Override
  public Response updateInstructor(
      @PathParam("instructorId") Long instructorId,
      @Valid InstructorUpdateRequest model) {
    Instructor entity = instructorService.update(instructorId, model);
    InstructorRestModel instructorRest = instructorMapper.toRest(entity);
    
    return Response.ok(instructorRest).build();
  }

  @DELETE
  @Path("/{instructorId}")
  @Override
  public Response deleteInstructor(@PathParam("instructorId") Long instructorId) {
    instructorService.delete(instructorId);
    return Response.ok().build();
  }

  @GET
  @Path("/{instructorId}")
  @Override
  public Response getInstructor(@PathParam("instructorId") Long instructorId) {
    Instructor entity = instructorService.getById(instructorId);
    InstructorRestModel rest = instructorMapper.toRest(entity);
    return Response.ok(rest).build();
  }

}
