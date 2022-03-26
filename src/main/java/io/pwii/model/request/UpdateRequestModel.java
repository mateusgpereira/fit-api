package io.pwii.model.request;

import java.util.List;
import io.pwii.model.enums.UpdateOperations;
import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequestModel<T> {

  @NotNull
  private UpdateOperations operation;

  @NotNull
  private List<T> values;

}
