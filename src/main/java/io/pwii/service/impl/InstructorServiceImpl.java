package io.pwii.service.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import io.pwii.entity.Instructor;
import io.pwii.mapper.InstructorMapper;
import io.pwii.model.InstructorRest;
import io.pwii.repository.InstructorRespository;
import io.pwii.service.InstructorService;
import io.quarkus.elytron.security.common.BcryptUtil;

@ApplicationScoped
public class InstructorServiceImpl implements InstructorService {

  @Inject
  private InstructorRespository instructorRespository;
  @Inject
  private InstructorMapper instructorMapper;

  @Override
  @Transactional
  public InstructorRest createInstructor(InstructorRest model) {
    Instructor entity = instructorMapper.toEntity(model);
    entity.setPassword(BcryptUtil.bcryptHash(entity.getPassword()));
    instructorRespository.persist(entity);
    return instructorMapper.toRest(entity);
  }
  
}
