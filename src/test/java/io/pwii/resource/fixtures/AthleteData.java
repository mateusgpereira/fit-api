package io.pwii.resource.fixtures;

import java.time.LocalDate;
import java.time.LocalDateTime;
import io.pwii.entity.Athlete;
import io.pwii.model.response.AthleteRestBriefModel;

public class AthleteData {

  private static String emailDomain = "@test.com";

  private static LocalDateTime localDateTimeNow = LocalDateTime.now();

  public static Athlete getAthleteEntity(Long id, String name) {
    return Athlete.builder()
        .id(id)
        .name(name)
        .age(21)
        .cpf("38555756065")
        .email(createEmail(name))
        .password("123rty")
        .createdAt(LocalDate.now())
        .updatedAt(localDateTimeNow)
        .build();
  }

  public static AthleteRestBriefModel getAthleteRestBriefModel(Long id, String name) {
    return AthleteRestBriefModel.builder()
      .id(id)
      .name(name)
      .email(createEmail(name))
      .build();
  }

  private static String createEmail(String name) {
    return String.join("", name, emailDomain);
  }

}
