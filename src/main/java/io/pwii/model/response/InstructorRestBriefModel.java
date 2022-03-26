package io.pwii.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstructorRestBriefModel {
  private Long id;

  private String name;

  private String email;

  private String phone;
}
