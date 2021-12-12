package io.pwii.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import io.pwii.entity.Workout;
import io.pwii.model.request.WorkoutRequestModel;
import io.pwii.model.request.WorkoutUpdateRequestModel;
import io.pwii.model.response.WorkoutRestModel;

@Mapper(
  componentModel = "cdi",
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WorkoutMapper {

  Workout toEntity(WorkoutRequestModel model);

  @Mappings({
    @Mapping(source = "athlete.id", target = "athleteId"),
    @Mapping(source = "instructor.id", target = "instructorId")
  })
  WorkoutRestModel toRest(Workout entity);

  void updateToEntity(WorkoutUpdateRequestModel model, @MappingTarget Workout entity);
  
}
