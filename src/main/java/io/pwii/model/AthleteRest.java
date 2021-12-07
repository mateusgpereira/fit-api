package io.pwii.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import io.pwii.entity.Instructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AthleteRest {

  private Long id;

  @NotBlank
  private String name;

  @Email
  private String email;

  @Min(value = 14)
  private short age;

  @NotBlank
  private String password;

  private String phone;

  @CPF
  private String cpf;

  private short height;

  private Double weight;

  private Long instructorId;

  public void setInstructorIdFromEntity(Instructor instructor) {
    if (instructor != null) {
      this.instructorId = instructor.getId();
    }
  }
  
}
