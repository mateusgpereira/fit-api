package io.pwii.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InstructorRestBriefModel {
  private Long id;

  private String name;

  private String email;

  private String phone;
}
