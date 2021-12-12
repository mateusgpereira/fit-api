package io.pwii.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import io.pwii.entity.GymClass;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.response.GymClassRestModel;

@Mapper(
  componentModel = "cdi",
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GymClassMapper {

  GymClass toEntity(GymClassRequestModel model);

  GymClassRestModel toRest(GymClass entity);
  
}
