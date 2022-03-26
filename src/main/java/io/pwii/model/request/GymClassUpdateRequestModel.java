package io.pwii.model.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import io.pwii.entity.enums.GymClassCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GymClassUpdateRequestModel {

  private String title;

  private GymClassCategory type;

  private LocalDate classDate;

  private LocalTime classTime;

  private Long instructorId;

  private Integer maxAthletes;

  private List<Long> athletesIds;

  private Integer reservedPlaces;

}
