package io.pwii.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import io.pwii.entity.Athlete;
import io.pwii.model.AthleteRest;
import io.pwii.model.AthleteUpdateRequest;
import io.pwii.model.response.AthleteRestBriefModel;

@Mapper(
    componentModel = "cdi",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AthleteMapper {

  @Mappings({
      @Mapping(source = "instructor.id", target = "instructorId")
  })
  AthleteRest toRest(Athlete entity);

  Athlete toEntity(AthleteRest rest);

  Athlete updateToEntity(AthleteUpdateRequest model, @MappingTarget Athlete entity);

  @Mapping(source = "instructor.id", target = "instructorId")
  AthleteRestBriefModel toBriefRest(Athlete entity);
}
