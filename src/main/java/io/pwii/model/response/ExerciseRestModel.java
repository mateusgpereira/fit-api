package io.pwii.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import io.pwii.entity.enums.WorkoutCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseRestModel {

  private Long id;

  private String title;

  private String description;

  private Integer reps;

  private Integer sets;

  private String time;

  private Double weight;

  private String equipment;

  private WorkoutCategory category;

  private Long workoutId;

  private LocalDate createdAt;

  private LocalDateTime updatedAt;
}
