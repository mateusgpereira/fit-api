package io.pwii.model.request;

import java.util.List;
import javax.validation.constraints.Min;
import io.pwii.entity.enums.WorkoutCategory;
import io.pwii.entity.enums.WorkoutCode;
import io.smallrye.common.constraint.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutRequestModel {

  @NotNull
  private WorkoutCode code;

  @NotNull
  private WorkoutCategory category;

  @NotNull
  @Min(value = 1)
  private Long instructorId;

  @NotNull
  @Min(value = 1)
  private Long athleteId;

  @NotNull
  private List<ExerciseRequestModel> exercises;

}
