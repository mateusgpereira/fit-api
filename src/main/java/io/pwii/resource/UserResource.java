package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import io.pwii.model.request.UserLoginRequest;

public interface UserResource {

  Response login(@Valid UserLoginRequest user);
  
}
