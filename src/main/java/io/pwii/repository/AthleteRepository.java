package io.pwii.repository;

import java.util.List;
import javax.inject.Singleton;
import io.pwii.entity.Athlete;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@Singleton
public class AthleteRepository implements PanacheRepositoryBase<Athlete, Long> {

  public List<Athlete> findAllById(List<Long> ids) {
    return list("id IN ?1", ids);
  }

}
