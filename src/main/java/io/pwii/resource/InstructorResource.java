package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import io.pwii.model.request.InstructorRequestModel;
import io.pwii.model.request.InstructorUpdateRequestModel;

public interface InstructorResource {

  Response createInstructor(@Valid InstructorRequestModel model);

  Response listInstructors(SecurityContext ctx, int page, int limit);

  Response updateInstructor(SecurityContext ctx, Long instructorId, @Valid InstructorUpdateRequestModel model);

  Response deleteInstructor(SecurityContext ctx, Long instructorId);

  Response getInstructor(SecurityContext ctx, Long instructorId);

}
