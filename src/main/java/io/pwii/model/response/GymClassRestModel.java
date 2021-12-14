package io.pwii.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import io.pwii.entity.enums.GymClassCategory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GymClassRestModel {

  private Long id;

  private String title;

  private GymClassCategory type;

  private LocalDate classDate;
  
  private LocalTime classTime;

  private InstructorRestBriefModel instructor;

  private Integer maxAthletes;

  private Set<AthleteRestBriefModel> athletes;

  private Integer reservedPlaces;

  private LocalDate createdAt;

  private LocalDateTime updatedAt;
  
}
