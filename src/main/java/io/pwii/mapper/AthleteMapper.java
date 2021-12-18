package io.pwii.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import io.pwii.entity.Athlete;
import io.pwii.model.request.AthleteRequestModel;
import io.pwii.model.request.AthleteUpdateRequest;
import io.pwii.model.response.AthleteRestModel;
import io.pwii.model.response.AthleteRestBriefModel;

@Mapper(
    componentModel = "cdi",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AthleteMapper {

  AthleteRestModel toRest(Athlete entity);

  Athlete toEntity(AthleteRequestModel rest);

  Athlete updateToEntity(AthleteUpdateRequest model, @MappingTarget Athlete entity);

  AthleteRestBriefModel toBriefRest(Athlete entity);
}
