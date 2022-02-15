package io.pwii.resource;

import static org.mockito.Mockito.when;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import io.pwii.entity.Exercise;
import io.pwii.entity.Workout;
import io.pwii.entity.enums.WorkoutCategory;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.model.response.ExerciseRestModel;
import io.pwii.resource.impl.ExerciseResourceImpl;
import io.pwii.service.ExerciseService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(ExerciseResourceImpl.class)
public class ExerciseResourceTest {

  @InjectMock
  private ExerciseService exerciseService;

  ExerciseRequestModel exerciseModelOne = ExerciseRequestModel.builder()
      .title("bench press")
      .reps(10)
      .sets(3)
      .weight(20.0)
      .category(WorkoutCategory.CHEST)
      .workoutId(1L)
      .build();


  Exercise exerciseEntityOne = Exercise.builder()
      .title("bench press")
      .reps(10)
      .sets(3)
      .weight(20.0)
      .category(WorkoutCategory.CHEST)
      .workout(Workout.builder().id(1L).build())
      .createdAt(LocalDate.now())
      .updatedAt(LocalDateTime.now())
      .build();

  ExerciseRestModel exerciseRestModel = ExerciseRestModel.builder()
      .title("bench press")
      .reps(10)
      .sets(3)
      .weight(20.0)
      .category(WorkoutCategory.CHEST)
      .workoutId(1L)
      .createdAt(LocalDate.now())
      .updatedAt(exerciseEntityOne.getUpdatedAt())
      .build();


  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldCreateExercise() {
    when(this.exerciseService.create(exerciseModelOne)).thenReturn(exerciseEntityOne);

    given()
        .contentType(ContentType.JSON)
        .body(exerciseModelOne)
        .when()
        .post().then().statusCode(201);
  }


}
