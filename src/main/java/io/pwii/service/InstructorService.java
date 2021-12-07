package io.pwii.service;

import io.pwii.entity.Instructor;
import io.pwii.model.PageModel;

public interface InstructorService {

  Instructor create(Instructor model);
  PageModel<Instructor> list(int page, int limit);
  
}
