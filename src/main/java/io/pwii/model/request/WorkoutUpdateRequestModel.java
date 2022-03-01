package io.pwii.model.request;

import java.util.List;
import io.pwii.entity.enums.WorkoutCategory;
import io.pwii.entity.enums.WorkoutCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutUpdateRequestModel {

  private WorkoutCode code;

  private WorkoutCategory category;

  private List<ExerciseUpdateRequestModel> exercises;
  
}
