package io.pwii.service;

import io.pwii.entity.User;
import io.pwii.model.request.UserLoginRequest;

public interface UserService {

  String login(UserLoginRequest userLoginRequest);
  
}
