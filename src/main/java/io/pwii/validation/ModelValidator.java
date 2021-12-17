package io.pwii.validation;

import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;

@ApplicationScoped
public class ModelValidator {

  @Inject
  private Validator validator;

  public <T> void validateModel(T model)
      throws BadRequestException {
    Set<ConstraintViolation<T>> violations = validator.validate(model);

    if (violations.size() > 0) {
      String message =
          violations.stream()
              .map(cv -> cv.getPropertyPath() + " " + cv.getMessage())
              .collect(Collectors.joining(", "));
      throw new BadRequestException(message);
    }
  }

}
