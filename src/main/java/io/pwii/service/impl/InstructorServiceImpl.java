package io.pwii.service.impl;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import io.pwii.entity.Instructor;
import io.pwii.entity.enums.UserRoles;
import io.pwii.mapper.InstructorMapper;
import io.pwii.model.InstructorUpdateRequest;
import io.pwii.model.PageModel;
import io.pwii.model.request.InstructorRequestModel;
import io.pwii.repository.InstructorRepository;
import io.pwii.service.InstructorService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@ApplicationScoped
public class InstructorServiceImpl implements InstructorService {

  @Inject
  private InstructorRepository instructorRepository;

  @Inject
  private InstructorMapper instructorMapper;

  @Transactional
  @Override
  public Instructor create(InstructorRequestModel model) {
    Instructor entity = instructorMapper.toEntity(model);
    entity.setPassword(BcryptUtil.bcryptHash(entity.getPassword()));
    entity.setRole(UserRoles.INSTRUCTOR);
    instructorRepository.persist(entity);
    return entity;
  }

  @Override
  public PageModel<Instructor> list(int page, int limit) {
    PanacheQuery<Instructor> allInstructors = instructorRepository.findAll();
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
    Optional<Instructor> optionalInstructor = instructorRepository.findByIdOptional(instructorId);
    if (optionalInstructor.isEmpty()) {
      throw new NotFoundException("Instructor Not Found");
    }

    Instructor entity = optionalInstructor.get();
    instructorMapper.updateToEntity(instructor, entity);

    if (instructor.getPassword() != null && !instructor.getPassword().isEmpty()) {
      entity.setPassword(BcryptUtil.bcryptHash(instructor.getPassword()));
    }

    instructorRepository.persist(entity);
    
    return entity;
  }

  @Transactional
  @Override
  public void delete(Long instructorId) {
    boolean wasDeleted = instructorRepository.deleteById(instructorId);

    if (!wasDeleted) {
      throw new BadRequestException("Something went wrong");
    }
  }

  @Override
  public Instructor getById(Long instructorId) {
    Optional<Instructor> optionalInstructor = instructorRepository.findByIdOptional(instructorId);
    if (optionalInstructor.isEmpty()) {
      throw new NotFoundException("Instructor Not Found");
    }

    return optionalInstructor.get();
  }
  
}
