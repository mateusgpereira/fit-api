package io.pwii.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import io.pwii.entity.Instructor;
import io.pwii.model.InstructorRest;
import io.pwii.model.InstructorUpdateRequest;
import io.pwii.model.response.InstructorRestBriefModel;

@Mapper(
    componentModel = "cdi",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InstructorMapper {

  Instructor toEntity(InstructorRest model);

  InstructorRest toRest(Instructor entity);

  void updateToEntity(InstructorUpdateRequest model, @MappingTarget Instructor entity);

  InstructorRestBriefModel toBriefRest(Instructor entity);
}
