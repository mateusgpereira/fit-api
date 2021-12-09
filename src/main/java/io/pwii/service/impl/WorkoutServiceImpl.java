package io.pwii.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import io.pwii.entity.Athlete;
import io.pwii.entity.Exercise;
import io.pwii.entity.Instructor;
import io.pwii.entity.Workout;
import io.pwii.mapper.ExerciseMapper;
import io.pwii.mapper.WorkoutMapper;
import io.pwii.model.request.WorkoutRequestModel;
import io.pwii.repository.AthleteRepository;
import io.pwii.repository.InstructorRepository;
import io.pwii.repository.WorkoutRepository;
import io.pwii.service.WorkoutService;

@ApplicationScoped
public class WorkoutServiceImpl implements WorkoutService {

  @Inject
  private WorkoutRepository workoutRepository;

  @Inject
  private InstructorRepository instructorRepository;

  @Inject
  private AthleteRepository athleteRepository;

  @Inject
  private WorkoutMapper workoutMapper;

  @Inject
  private ExerciseMapper exerciseMapper;

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

}
