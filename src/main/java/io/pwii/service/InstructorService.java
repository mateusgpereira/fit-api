package io.pwii.service;

import io.pwii.entity.Instructor;
import io.pwii.model.InstructorRest;
import io.pwii.model.InstructorUpdateRequest;
import io.pwii.model.PageModel;

public interface InstructorService {

  Instructor create(InstructorRest model);

  PageModel<Instructor> list(int page, int limit);

  Instructor update(Long instructorId, InstructorUpdateRequest instructor);

}
