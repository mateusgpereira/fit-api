package io.pwii.service;

import io.pwii.entity.Instructor;
import io.pwii.model.PageModel;
import io.pwii.model.request.InstructorRequestModel;
import io.pwii.model.request.InstructorUpdateRequestModel;

public interface InstructorService {

  Instructor create(InstructorRequestModel model);

  PageModel<Instructor> list(int page, int limit);

  Instructor update(Long instructorId, InstructorUpdateRequestModel instructor);

  void delete(Long instructorId);

  Instructor getById(Long instructorId);

}
