package io.pwii.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import io.pwii.entity.Athlete;
import io.pwii.model.AthleteRest;
import io.pwii.model.AthleteUpdateRequest;
import io.pwii.model.response.AthleteRestBriefModel;

@Mapper(
    componentModel = "cdi",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AthleteMapper {

  AthleteRest toRest(Athlete entity);

  Athlete toEntity(AthleteRest rest);

  Athlete updateToEntity(AthleteUpdateRequest model, @MappingTarget Athlete entity);

  AthleteRestBriefModel toBriefRest(Athlete entity);
}
