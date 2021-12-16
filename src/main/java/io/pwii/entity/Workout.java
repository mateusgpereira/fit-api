package io.pwii.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.pwii.entity.enums.WorkoutCategory;
import io.pwii.entity.enums.WorkoutCode;
import lombok.Data;

@Entity
@Data
public class Workout {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 1, nullable = false)
  @Enumerated(EnumType.STRING)
  private WorkoutCode code;

  @Column(length = 20, nullable = false)
  @Enumerated(EnumType.STRING)
  private WorkoutCategory category;

  @ManyToOne(fetch = FetchType.LAZY)
  private Instructor instructor;

  @ManyToOne(fetch = FetchType.LAZY)
  private Athlete athlete;

  @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
  private Set<Exercise> exercises = new HashSet<>();

  @CreationTimestamp
  private LocalDate createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public void setExercises(Set<Exercise> exercises) {
    this.exercises = exercises;
  }

  public void setExercises(List<Exercise> exercises) {
    this.exercises = new HashSet<>(exercises);
  }

  public void addToExercises(Exercise exercise) {
    if (this.exercises.add(exercise)) {
      exercise.setWorkout(this);
    }
  }

  public boolean removeFromExercises(Exercise exercise) {
    return this.exercises.remove(exercise);
  }

  public void removeFromExercises(Collection<Exercise> exercises) {
    this.exercises.removeAll(exercises);
  }

  public void addToExercises(List<Exercise> exercises) {
    this.exercises.forEach(this::addToExercises);;
  }

  public boolean removeExerciseById(Long exerciseId) {
    Exercise exercise = this.exercises.stream()
        .filter(item -> item.getId().equals(exerciseId))
        .findFirst()
        .orElse(null);
    
    if (exercise == null) {
      return false;
    }

    return this.removeFromExercises(exercise);
  }

  public void removeAllFromExercisesById(List<Long> exercisesIds) {
    exercisesIds.forEach(this::removeExerciseById);
  }

}
