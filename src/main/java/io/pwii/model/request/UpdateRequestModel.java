package io.pwii.model.request;

import java.util.List;
import io.pwii.model.enums.UpdateOperations;
import io.smallrye.common.constraint.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateRequestModel<T> {

  @NotNull
  private UpdateOperations operation;

  @NotNull
  private List<T> values;

}
