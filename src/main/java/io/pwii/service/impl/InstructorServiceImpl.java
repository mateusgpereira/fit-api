package io.pwii.service.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import io.pwii.entity.Instructor;
import io.pwii.repository.InstructorRespository;
import io.pwii.service.InstructorService;
import io.quarkus.elytron.security.common.BcryptUtil;

@ApplicationScoped
public class InstructorServiceImpl implements InstructorService {

  @Inject
  private InstructorRespository instructorRespository;

  @Override
  @Transactional
  public Instructor createInstructor(Instructor entity) {
    entity.setPassword(BcryptUtil.bcryptHash(entity.getPassword()));
    instructorRespository.persist(entity);
    return entity;
  }
  
}
