package io.pwii.resource.fixtures;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import io.pwii.entity.GymClass;
import io.pwii.entity.enums.GymClassCategory;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.response.GymClassRestModel;

public class GymClassData {

  private static LocalTime classTime = LocalTime.now();
  private static LocalDate classDate = LocalDate.now();
  private static LocalDateTime updatedAt = LocalDateTime.now();
  private static final String YOGA = "Yoga";

  public static GymClassRequestModel getGymClassRequestModel() {
    return GymClassRequestModel.builder()
        .classDate(classDate)
        .classTime(classTime)
        .instructorId(1L)
        .maxAthletes(20)
        .title(YOGA)
        .type(GymClassCategory.YOGA)
        .build();
  }

  public static GymClass getGymClassEntity() {
    return GymClass.builder()
        .id(1L)
        .classDate(classDate)
        .classTime(classTime)
        .instructor(InstructorData.createInstructor("john"))
        .maxAthletes(20)
        .title(YOGA)
        .type(GymClassCategory.YOGA)
        .createdAt(classDate)
        .updatedAt(updatedAt)
        .build();
  }

  public static GymClassRestModel getGymClassRestModel() {
    return GymClassRestModel.builder()
        .id(1L)
        .classDate(classDate)
        .classTime(classTime)
        .instructor(InstructorData.createInstruRestBriefModel("john"))
        .maxAthletes(20)
        .title(YOGA)
        .type(GymClassCategory.YOGA)
        .createdAt(classDate)
        .updatedAt(updatedAt)
        .build();
  }

}
