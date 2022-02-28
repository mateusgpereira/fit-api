package io.pwii.model.request;

import java.util.List;
import javax.validation.constraints.Min;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import io.pwii.entity.enums.WorkoutCategory;
import io.pwii.entity.enums.WorkoutCode;
import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequestModel {

  @NotNull
  @Schema(required = true)
  private WorkoutCode code;

  @NotNull
  @Schema(required = true)
  private WorkoutCategory category;

  @NotNull
  @Min(value = 1)
  @Schema(required = true)
  private Long instructorId;

  @NotNull
  @Min(value = 1)
  @Schema(required = true)
  private Long athleteId;

  @NotNull
  @Schema(required = true)
  private List<ExerciseRequestModel> exercises;

}
