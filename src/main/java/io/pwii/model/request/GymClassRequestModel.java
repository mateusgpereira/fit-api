package io.pwii.model.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import io.pwii.entity.enums.GymClassCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GymClassRequestModel {

  @NotBlank
  private String title;

  @NotNull
  private GymClassCategory type;

  @NotNull
  private LocalDate classDate;

  @NotNull
  private LocalTime classTime;

  @NotNull
  @Min(value = 1)
  private Long instructorId;

  private Integer maxAthletes;

  private List<Long> athletesIds;

  private Integer reservedPlaces;
  
}
