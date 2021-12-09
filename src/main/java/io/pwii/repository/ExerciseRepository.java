package io.pwii.repository;

import javax.inject.Singleton;
import io.pwii.entity.Exercise;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@Singleton
public class ExerciseRepository implements PanacheRepositoryBase<Exercise, Long> {

}
