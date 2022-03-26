package io.pwii.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import io.pwii.entity.enums.GymClassCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GymClassRestModel {

  private Long id;

  private String title;

  private GymClassCategory type;

  private LocalDate classDate;
  
  private LocalTime classTime;

  private InstructorRestBriefModel instructor;

  private Integer maxAthletes;

  @Builder.Default
  private Set<AthleteRestBriefModel> athletes = new HashSet<>();

  private Integer reservedPlaces;

  private LocalDate createdAt;

  private LocalDateTime updatedAt;
  
}
