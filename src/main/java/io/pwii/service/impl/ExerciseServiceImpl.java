package io.pwii.service.impl;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import io.pwii.entity.Exercise;
import io.pwii.mapper.ExerciseMapper;
import io.pwii.model.PageModel;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.model.request.ExerciseUpdateRequestModel;
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

  @Transactional
  @Override
  public Exercise update(Long exerciseId, ExerciseUpdateRequestModel exercise) {
    Optional<Exercise> optionalExercise = exerciseRepository.findByIdOptional(exerciseId);

    if (optionalExercise.isEmpty()) {
      throw new NotFoundException("Exercise Not Found");
    }

    Exercise entity = optionalExercise.get();
    exerciseMapper.updateToEntity(exercise, entity);
    exerciseRepository.persist(entity);
    return entity;
  }

  @Transactional
  @Override
  public void delete(Long exerciseId) {
    boolean wasDeleted = exerciseRepository.deleteById(exerciseId);
    if (!wasDeleted) {
      throw new BadRequestException("Something went wrong");
    }
    
  }

  @Override
  public Exercise getById(Long exerciseId) {
    Optional<Exercise> optionalExercise = exerciseRepository.findByIdOptional(exerciseId);
    if (optionalExercise.isEmpty()) {
      throw new NotFoundException("Exercise Not Found");
    }

    return optionalExercise.get();
  }

}
