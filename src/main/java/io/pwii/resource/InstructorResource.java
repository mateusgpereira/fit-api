package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.InstructorRest;
import io.pwii.model.InstructorUpdateRequest;

public interface InstructorResource {

  Response createInstructor(@Valid InstructorRest model);

  Response listInstructors(int page, int limit);

  Response updateInstructor(Long instructorId, @Valid InstructorUpdateRequest model);

  Response deleteInstructor(Long instructorId);

}
