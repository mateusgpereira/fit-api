package io.pwii.resource.fixtures;

import io.pwii.model.request.UserLoginRequest;

public class UserLoginData {

  public static UserLoginRequest createUserLoginRequest() {
    return UserLoginRequest.builder().email("john@test.com").password("superstrongpass").build();
  }

}
