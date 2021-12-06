package io.pwii.repository;

import javax.inject.Singleton;
import io.pwii.entity.Instructor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@Singleton
public class InstructorRepository implements PanacheRepositoryBase<Instructor, Long> {
  
}
