package io.pwii.repository;

import javax.inject.Singleton;
import io.pwii.entity.Workout;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Singleton
public class WorkoutRepository implements PanacheRepository<Workout> {
  
}
