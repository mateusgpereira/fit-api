package io.pwii.repository;

import javax.inject.Singleton;
import io.pwii.entity.Athlete;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@Singleton
public class AthleteRepository implements PanacheRepositoryBase<Athlete, Long> {

}
