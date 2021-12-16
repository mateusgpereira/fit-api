package io.pwii.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import io.pwii.entity.enums.WorkoutCategory;
import io.pwii.entity.enums.WorkoutCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutRestModel {

  private Long id;

  private WorkoutCode code;

  private WorkoutCategory category;

  private Long instructorId;

  private Long athleteId;

  private Set<ExerciseRestModel> exercises;

  private LocalDate createdAt;

  private LocalDateTime updatedAt;
  
}
