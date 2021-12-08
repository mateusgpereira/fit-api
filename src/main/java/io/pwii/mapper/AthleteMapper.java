package io.pwii.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import io.pwii.entity.Athlete;
import io.pwii.model.AthleteRest;

@Mapper(componentModel = "cdi")
public interface AthleteMapper {

  @Mappings({
    @Mapping(source = "instructor.id", target = "instructorId")
  })
  AthleteRest toRest(Athlete entity);

  Athlete toEntity(AthleteRest rest);
}
