package io.pwii.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {

  private String message;
  private String token;

}
