package io.pwii.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import io.pwii.entity.Exercise;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.model.request.ExerciseUpdateRequestModel;
import io.pwii.model.response.ExerciseRestModel;

@Mapper(
  componentModel = "cdi",
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ExerciseMapper {

  Exercise toEntity(ExerciseRequestModel model);

  @Mapping(source = "workout.id", target = "workoutId")
  ExerciseRestModel toRest(Exercise entity);

  Exercise updateToEntity(ExerciseUpdateRequestModel model, @MappingTarget Exercise entity);
  
};
