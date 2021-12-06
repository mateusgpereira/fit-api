package io.pwii.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import io.pwii.entity.Instructor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@Singleton
public class InstructorRespository implements PanacheRepositoryBase<Instructor, Long> {
  
}
