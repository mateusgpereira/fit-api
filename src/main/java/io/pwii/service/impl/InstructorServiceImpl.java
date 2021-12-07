package io.pwii.service.impl;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import io.pwii.entity.Instructor;
import io.pwii.model.PageModel;
import io.pwii.repository.InstructorRepository;
import io.pwii.service.InstructorService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@ApplicationScoped
public class InstructorServiceImpl implements InstructorService {

  @Inject
  private InstructorRepository instructorRespository;

  @Override
  @Transactional
  public Instructor create(Instructor entity) {
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
      .content(listPaginated)
      .build();
  }
  
}
