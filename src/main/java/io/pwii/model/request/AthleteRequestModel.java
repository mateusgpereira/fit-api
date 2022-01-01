package io.pwii.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.br.CPF;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AthleteRequestModel {

  @NotBlank
  private String name;

  @Email
  @NotNull
  @Schema(required = true)
  private String email;

  @Min(value = 14)
  private Integer age;

  @NotBlank
  private String password;

  private String phone;

  @CPF
  @NotNull
  @Schema(required = true)
  private String cpf;

  private Integer height;

  private Double weight;
  
}
