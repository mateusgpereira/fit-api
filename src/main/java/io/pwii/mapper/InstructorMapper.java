package io.pwii.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import io.pwii.entity.Instructor;
import io.pwii.model.InstructorRest;
import io.pwii.model.InstructorUpdateRequest;

@Mapper(componentModel = "cdi")
public interface InstructorMapper {
  
  Instructor toEntity(InstructorRest model);
  InstructorRest toRest(Instructor entity);
  void updateToEntity(InstructorUpdateRequest model, @MappingTarget Instructor entity);
}
