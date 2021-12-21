package io.pwii.repository;

import java.util.Optional;
import javax.inject.Singleton;
import io.pwii.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@Singleton
public class UserRepository implements PanacheRepositoryBase<User, Long> {
  public Optional<User> findOneByEmail(String email) {
    return find("email = ?1", email).firstResultOptional();
  }
}
