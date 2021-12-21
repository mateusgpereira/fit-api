package io.pwii.service.impl;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import io.pwii.entity.User;
import io.pwii.model.request.UserLoginRequest;
import io.pwii.repository.UserRepository;
import io.pwii.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class UserServiceImpl implements UserService {

  @Inject
  private UserRepository userRepository;

  @ConfigProperty(name = "mp.jwt.verify.issuer")
  private String issuer;

  @Override
  public String login(UserLoginRequest userLoginRequest) {
    Optional<User> optionalUser = userRepository.findOneByEmail(userLoginRequest.getEmail());

    if (optionalUser.isEmpty()) {
      throw new BadRequestException("Invalid User Credentials");
    }

    User user = optionalUser.get();

    if (!BcryptUtil.matches(userLoginRequest.getPassword(), user.getPassword())) {
      throw new BadRequestException("Invalid User Credentials");
    }

    return Jwt.issuer(issuer)
        .upn(user.getEmail())
        .groups(user.getGroupsByRole())
        .claim(Claims.birthdate.name(), "2021-12-21")
        .sign();
  }

}
