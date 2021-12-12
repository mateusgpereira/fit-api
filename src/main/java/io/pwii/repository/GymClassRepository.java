package io.pwii.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import javax.inject.Singleton;
import io.pwii.entity.GymClass;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Singleton
public class GymClassRepository implements PanacheRepository<GymClass> {

  public Optional<GymClass> findOneByClassDateAndClassTimeAndInstructor(LocalDate classDate,
      LocalTime classTime, Long instructorId) {
    Map<String, Object> params =
        Map.of("instructorId", instructorId, "classDate", classDate, "classTime", classTime);

    return find("instructor_id = :instructorId AND classDate = :classDate AND classTime = :classTime",
        params).firstResultOptional();
  }
}
