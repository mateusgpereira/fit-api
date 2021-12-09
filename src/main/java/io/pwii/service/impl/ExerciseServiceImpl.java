package io.pwii.service.impl;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import io.pwii.entity.Exercise;
import io.pwii.mapper.ExerciseMapper;
import io.pwii.model.PageModel;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.repository.ExerciseRepository;
import io.pwii.service.ExerciseService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@ApplicationScoped
public class ExerciseServiceImpl implements ExerciseService {

  @Inject
  private ExerciseRepository exerciseRepository;

  @Inject
  private ExerciseMapper exerciseMapper;

  @Transactional
  @Override
  public Exercise create(ExerciseRequestModel exercise) {
    Exercise entity = exerciseMapper.toEntity(exercise);

    exerciseRepository.persist(entity);

    return entity;
  }

  @Override
  public PageModel<Exercise> list(int page, int limit) {
    PanacheQuery<Exercise> allExercises = exerciseRepository.findAll();
    List<Exercise> currentPageList = allExercises.page(page, limit).list();
    return PageModel.<Exercise>builder()
        .content(currentPageList)
        .currentPage(page)
        .currentPageTotalItems(currentPageList.size())
        .numberOfPages(allExercises.pageCount())
        .totalItems(allExercises.count())
        .build();
  }

}
