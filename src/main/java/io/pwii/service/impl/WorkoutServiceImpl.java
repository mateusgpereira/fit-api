package io.pwii.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import org.jboss.logging.Logger;
import io.pwii.entity.Athlete;
import io.pwii.entity.Exercise;
import io.pwii.entity.Instructor;
import io.pwii.entity.Workout;
import io.pwii.mapper.ExerciseMapper;
import io.pwii.mapper.WorkoutMapper;
import io.pwii.model.PageModel;
import io.pwii.model.enums.UpdateOperations;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.model.request.UpdateRequestModel;
import io.pwii.model.request.WorkoutExerciseUpdateRequestModel;
import io.pwii.model.request.WorkoutRequestModel;
import io.pwii.model.request.WorkoutUpdateRequestModel;
import io.pwii.repository.AthleteRepository;
import io.pwii.repository.ExerciseRepository;
import io.pwii.repository.InstructorRepository;
import io.pwii.repository.WorkoutRepository;
import io.pwii.service.WorkoutService;
import io.pwii.validation.ModelValidator;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@ApplicationScoped
public class WorkoutServiceImpl implements WorkoutService {

  @Inject
  private WorkoutRepository workoutRepository;

  @Inject
  private InstructorRepository instructorRepository;

  @Inject
  private AthleteRepository athleteRepository;

  @Inject
  private ExerciseRepository exerciseRepository;

  @Inject
  private WorkoutMapper workoutMapper;

  @Inject
  private ExerciseMapper exerciseMapper;

  @Inject
  Logger logger;

  @Inject
  private ModelValidator modelValidator;

  @Transactional
  @Override
  public Workout create(WorkoutRequestModel workout) {
    if (workout.getExercises().size() < 1) {
      throw new BadRequestException("Exercises are mandatory.");
    }

    Optional<Instructor> optionalInstructor =
        instructorRepository.findByIdOptional(workout.getInstructorId());
    if (optionalInstructor.isEmpty()) {
      throw new BadRequestException("Invalid Instructor.");
    }

    Optional<Athlete> optionalAthlete = athleteRepository.findByIdOptional(workout.getAthleteId());
    if (optionalAthlete.isEmpty()) {
      throw new BadRequestException("Invalid Athlete.");
    }

    Workout entity = workoutMapper.toEntity(workout);
    entity.setAthlete(optionalAthlete.get());
    entity.setInstructor(optionalInstructor.get());

    List<Exercise> exerciseList = workout.getExercises().stream()
        .map(exercise -> {
          Exercise exerciseEntity = exerciseMapper.toEntity(exercise);
          exerciseEntity.setWorkout(entity);
          return exerciseEntity;
        })
        .collect(Collectors.toList());

    entity.setExercises(exerciseList);

    workoutRepository.persist(entity);

    return entity;
  }

  @Override
  public PageModel<Workout> list(int page, int limit) {
    PanacheQuery<Workout> allWorkouts = workoutRepository.findAll();
    List<Workout> currentPageList = allWorkouts.page(page, limit).list();

    return PageModel.<Workout>builder()
        .content(currentPageList)
        .currentPage(page)
        .currentPageTotalItems(currentPageList.size())
        .numberOfPages(allWorkouts.pageCount())
        .totalItems(allWorkouts.count())
        .build();
  }

  @Transactional
  @Override
  public Workout update(Long workoutId, WorkoutUpdateRequestModel workout) {
    Workout entity = this.findWorkoutById(workoutId);
    workoutMapper.updateToEntity(workout, entity);

    if (workout.getExercises().size() > 0) {
      entity.removeFromExercises(entity.getExercises());
      exerciseRepository.deleteAllByWorkoutId(workoutId);
      List<Exercise> exercises = workout.getExercises()
          .stream()
          .map(exercise -> {
            Exercise exerciseEntity = exerciseMapper.updateToEntity(exercise);
            exerciseEntity.setWorkout(entity);
            return exerciseEntity;
          })
          .collect(Collectors.toList());
      entity.setExercises(exercises);
    }

    workoutRepository.persist(entity);
    return entity;
  }

  @Transactional
  @Override
  public void delete(Long workoutId) {
    boolean wasDeleted = workoutRepository.deleteById(workoutId);
    if (!wasDeleted) {
      throw new BadRequestException("Something went wrong");
    }
  }

  @Override
  public Workout getById(Long workoutId) {
    return this.findWorkoutById(workoutId);
  }

  @Transactional
  @Override
  public Workout updateExercises(Long workoutId,
      List<WorkoutExerciseUpdateRequestModel> data) {
    Workout workout = this.findWorkoutById(workoutId);

    if (data.size() < 1) {
      throw new BadRequestException("Nothing To Update.");
    }

    this.validateWorkoutExercisesRequestBody(data);

    data.forEach(item -> {
      if (item.getOperation() == UpdateOperations.REMOVE && item.getIds().size() > 0) {
        workout.removeAllFromExercisesById(item.getIds());
        exerciseRepository.deleteAllByIdIn(item.getIds());
        return;
      }

      if (item.getOperation() == UpdateOperations.ADD && item.getValues().size() > 0) {
        List<Exercise> entityList = item.getValues().stream()
            .map(exerciseMapper::toEntity)
            .collect(Collectors.toList());

        workout.addToExercises(entityList);
        return;
      }
    });

    workoutRepository.persist(workout);
    return workout;
  }

  @Transactional
  @Override
  public Workout removeExercise(Long workoutId, Long exerciseId) {
    Workout entity = this.findWorkoutById(workoutId);

    boolean wasRemoved = entity.removeExerciseById(exerciseId);
    if (!wasRemoved) {
      throw new BadRequestException("No exercise removed");
    }

    exerciseRepository.deleteById(exerciseId);
    workoutRepository.persist(entity);
    return entity;
  }

  private Workout findWorkoutById(Long workoutId) throws NotFoundException {
    Optional<Workout> optionalWorkout = workoutRepository.findByIdOptional(workoutId);
    if (optionalWorkout.isEmpty()) {
      throw new NotFoundException("Workout Not Found");
    }
    return optionalWorkout.get();
  }

  private void validateWorkoutExercisesRequestBody(List<WorkoutExerciseUpdateRequestModel> data) {
    data.forEach(item -> {
      if (!item.validateProperties()) {
        throw new BadRequestException(
            "ids is mandatory for REMOVE operation | values is mandatory for ADD operation");
      }
    });
  }

}
