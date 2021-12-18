package io.pwii.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AthleteRestModel {

  private Long id;

  private String name;

  private String email;

  private Integer age;

  private String phone;

  private String cpf;

  private Integer height;

  private Double weight;

  private LocalDate createdAt;

  private LocalDateTime updatedAt;
  
}
