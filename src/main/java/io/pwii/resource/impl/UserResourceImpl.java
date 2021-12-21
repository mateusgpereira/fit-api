package io.pwii.resource.impl;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import io.pwii.model.request.UserLoginRequest;
import io.pwii.model.response.LoginResponse;
import io.pwii.resource.UserResource;
import io.pwii.service.UserService;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResourceImpl implements UserResource {

  @Inject
  private UserService userService;

  @POST
  @PermitAll
  @Override
  public Response login(@Context SecurityContext ctx, @Valid UserLoginRequest user) {
    String token = userService.login(user);
    LoginResponse response = new LoginResponse();
    if (token.isBlank()) {
      response.setMessage("Invalid Credentials. Check the data and try again");
    } else {
      response.setMessage("Login Successful");
      response.setToken(token);
    }

    return Response.ok(response).build();
  }
  
}
