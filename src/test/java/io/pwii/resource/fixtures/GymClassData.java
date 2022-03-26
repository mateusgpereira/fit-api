package io.pwii.resource.fixtures;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.pwii.entity.GymClass;
import io.pwii.entity.enums.GymClassCategory;
import io.pwii.model.PageModel;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.request.GymClassUpdateRequestModel;
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

  public static GymClass getGymClassEntity(Long id, String title, String instructorName, Integer maxAthletes) {
    return GymClass.builder()
        .id(id)
        .classDate(classDate)
        .classTime(classTime)
        .instructor(InstructorData.createInstructor(instructorName))
        .maxAthletes(maxAthletes)
        .title(title)
        .type(GymClassCategory.YOGA)
        .createdAt(classDate)
        .updatedAt(updatedAt)
        .build();
  }

  public static GymClassRestModel getGymClassRestModel(Long id, String title, String instructorName, Integer maxAthletes) {
    return GymClassRestModel.builder()
        .id(id)
        .classDate(classDate)
        .classTime(classTime)
        .instructor(InstructorData.createInstruRestBriefModel(instructorName))
        .maxAthletes(maxAthletes)
        .title(title)
        .type(GymClassCategory.YOGA)
        .createdAt(classDate)
        .updatedAt(updatedAt)
        .build();
  }

  public static PageModel<GymClass> getGymClassPage() {
    List<GymClass> content = new ArrayList<>(
        Arrays.asList(getGymClassEntity(1L, YOGA, "john", 20), getGymClassEntity(2L, YOGA, "marie", 20)));
    return PageModel.<GymClass>builder()
        .content(content)
        .currentPage(0)
        .currentPageTotalItems(2)
        .numberOfPages(1)
        .totalItems(2L)
        .build();
  }

  public static GymClassUpdateRequestModel getGymClassUpdateModel(String title,
      Integer maxAthletes) {
    return GymClassUpdateRequestModel.builder()
        .title(title)
        .maxAthletes(maxAthletes)
        .build();
  }

}
