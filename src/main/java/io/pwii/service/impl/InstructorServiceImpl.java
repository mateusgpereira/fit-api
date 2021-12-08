package io.pwii.service.impl;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import io.pwii.entity.Instructor;
import io.pwii.mapper.InstructorMapper;
import io.pwii.model.InstructorRest;
import io.pwii.model.InstructorUpdateRequest;
import io.pwii.model.PageModel;
import io.pwii.repository.InstructorRepository;
import io.pwii.service.InstructorService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@ApplicationScoped
public class InstructorServiceImpl implements InstructorService {

  @Inject
  private InstructorRepository instructorRespository;

  @Inject
  private InstructorMapper instructorMapper;

  @Transactional
  @Override
  public Instructor create(InstructorRest model) {
    Instructor entity = instructorMapper.toEntity(model);
    entity.setPassword(BcryptUtil.bcryptHash(entity.getPassword()));
    instructorRespository.persist(entity);
    return entity;
  }

  @Override
  public PageModel<Instructor> list(int page, int limit) {
    PanacheQuery<Instructor> allInstructors = instructorRespository.findAll();
    List<Instructor> listPaginated = allInstructors.page(page, limit).list();

    return PageModel.<Instructor>builder()
      .totalItems(allInstructors.count())
      .numberOfPages(allInstructors.pageCount())
      .currentPageTotalItems(listPaginated.size())
      .currentPage(page)
      .content(listPaginated)
      .build();
  }

  @Transactional
  @Override
  public Instructor update(Long instructorId, InstructorUpdateRequest instructor) {
    Optional<Instructor> optionalInstructor = instructorRespository.findByIdOptional(instructorId);
    if (optionalInstructor.isEmpty()) {
      throw new RuntimeException("Instructor not found");
    }

    Instructor entity = optionalInstructor.get();
    instructorMapper.updateToEntity(instructor, entity);

    if (instructor.getPassword() != null && !instructor.getPassword().isEmpty()) {
      entity.setPassword(BcryptUtil.bcryptHash(instructor.getPassword()));
    }

    instructorRespository.persist(entity);
    
    return entity;
  }
  
}
