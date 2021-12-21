package io.pwii.resource;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import io.pwii.model.request.UserLoginRequest;

public interface UserResource {

  Response login(SecurityContext ctx, @Valid UserLoginRequest user);
  
}
