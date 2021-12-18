package io.pwii.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorRestModel {

  private Long id;

  private String name;

  private String email;

  private Integer age;

  private String phone;

  private String cpf;

  private LocalDate createdAt;

  private LocalDateTime updatedAt;
  
}
