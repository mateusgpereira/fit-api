package io.pwii.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.br.CPF;
import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InstructorRequestModel {

  @NotBlank
  private String name;

  @Email
  @NotNull
  @Schema(required = true)
  private String email;

  @Min(value = 16)
  private Integer age;

  @NotBlank
  private String password;

  private String phone;

  @CPF
  @Schema(required = true)
  private String cpf;

}
