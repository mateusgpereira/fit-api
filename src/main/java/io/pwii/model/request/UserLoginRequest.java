package io.pwii.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import io.smallrye.common.constraint.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginRequest {

  @NotNull
  @Email
  private String email;
  @NotBlank
  private String password;

}
