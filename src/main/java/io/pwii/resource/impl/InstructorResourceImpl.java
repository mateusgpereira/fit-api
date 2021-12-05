package io.pwii.resource.impl;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.pwii.entity.Instructor;
import io.pwii.mapper.InstructorMapper;
import io.pwii.model.InstructorRest;
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

  @Override
  @POST
  public Response createInstructor(@Valid InstructorRest model) {
    final Instructor created = instructorService.createInstructor(instructorMapper.toEntity(model));
    InstructorRest createdRest = instructorMapper.toRest(created);
    return Response.status(Response.Status.CREATED).entity(createdRest).build();
  }
  
}
