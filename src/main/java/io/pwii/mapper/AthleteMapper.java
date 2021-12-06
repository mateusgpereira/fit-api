package io.pwii.mapper;

import org.mapstruct.Mapper;
import io.pwii.entity.Athlete;
import io.pwii.model.AthleteRest;

@Mapper(componentModel = "cdi")
public interface AthleteMapper {

  AthleteRest toRest(Athlete entity);

  Athlete toEntity(AthleteRest rest);
}
