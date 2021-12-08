package io.pwii.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorRest {

  private Long id;

  @NotBlank
  private String name;

  @Email
  @NotBlank
  private String email;

  @Min(value = 16)
  private Integer age;

  @NotBlank
  private String password;

  private String phone;

  @CPF
  private String cpf;
  
}
