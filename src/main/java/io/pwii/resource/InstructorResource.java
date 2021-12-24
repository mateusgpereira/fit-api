package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import io.pwii.model.InstructorUpdateRequest;
import io.pwii.model.request.InstructorRequestModel;

public interface InstructorResource {

  Response createInstructor(@Valid InstructorRequestModel model);

  Response listInstructors(SecurityContext ctx, int page, int limit);

  Response updateInstructor(SecurityContext ctx, Long instructorId, @Valid InstructorUpdateRequest model);

  Response deleteInstructor(SecurityContext ctx, Long instructorId);

  Response getInstructor(SecurityContext ctx, Long instructorId);

}
