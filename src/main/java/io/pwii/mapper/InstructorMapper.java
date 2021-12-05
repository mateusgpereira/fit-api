package io.pwii.mapper;

import org.mapstruct.Mapper;
import io.pwii.entity.Instructor;
import io.pwii.model.InstructorRest;

@Mapper(componentModel = "cdi")
public interface InstructorMapper {
  
  Instructor toEntity(InstructorRest model);
  InstructorRest toRest(Instructor entity);
}
