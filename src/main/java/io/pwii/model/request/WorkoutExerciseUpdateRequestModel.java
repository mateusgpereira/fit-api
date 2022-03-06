package io.pwii.model.request;

import java.util.List;
import io.pwii.model.enums.UpdateOperations;
import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutExerciseUpdateRequestModel {
 
  @NotNull
  private UpdateOperations operation;

  private List<Long> ids;

  private List<ExerciseRequestModel> values;

  public boolean validateProperties() {
    if (this.operation == UpdateOperations.ADD) {
      return this.values != null && this.values.size() > 0;
    }

    if (this.operation == UpdateOperations.REMOVE) {
      return this.ids != null && this.ids.size() > 0;
    }

    return false;
  }
  
}
