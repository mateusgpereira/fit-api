package io.pwii.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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
