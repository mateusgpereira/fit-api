package io.pwii.resource.fixtures;

import java.time.LocalDate;
import java.time.LocalDateTime;
import io.pwii.entity.Instructor;
import io.pwii.model.response.InstructorRestBriefModel;

public class InstructorData {

  private static String emailDomain = "@test.com";

  private static LocalDateTime localDateTimeNow = LocalDateTime.now();

  public static Instructor createInstructor(String name) {
    return Instructor.builder()
        .id(1L)
        .name(name)
        .cpf("20387648089")
        .email(String.join("", name, emailDomain))
        .password("123rty")
        .phone("51991299483")
        .age(28)
        .createdAt(LocalDate.now())
        .updatedAt(localDateTimeNow)
        .build();
  }

  public static InstructorRestBriefModel createInstruRestBriefModel(String name) {
    return InstructorRestBriefModel.builder()
        .email(String.join("", name, emailDomain))
        .id(1L)
        .name(name)
        .phone("51991299483")
        .build();
  }

}
