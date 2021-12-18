package io.pwii.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InstructorRequestModel {

  @NotBlank
  private String name;
  
  private String email;

  @Min(value = 16)
  private Integer age;

  @NotBlank
  private String password;

  private String phone;

  @CPF
  private String cpf;
  
}
