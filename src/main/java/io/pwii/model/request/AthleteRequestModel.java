package io.pwii.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.br.CPF;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AthleteRequestModel {

  @NotBlank
  private String name;

  @Email
  @Schema(required = true)
  private String email;

  @Min(value = 14)
  private Integer age;

  @NotBlank
  private String password;

  private String phone;

  @CPF
  @Schema(required = true)
  private String cpf;

  private Integer height;

  private Double weight;
  
}
