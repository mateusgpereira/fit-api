package io.pwii.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {

  @NotNull
  @Email
  private String email;
  @NotBlank
  private String password;

}
