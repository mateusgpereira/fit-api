package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.InstructorUpdateRequest;
import io.pwii.model.request.InstructorRequestModel;

public interface InstructorResource {

  Response createInstructor(@Valid InstructorRequestModel model);

  Response listInstructors(int page, int limit);

  Response updateInstructor(Long instructorId, @Valid InstructorUpdateRequest model);

  Response deleteInstructor(Long instructorId);

  Response getInstructor(Long instructorId);

}
