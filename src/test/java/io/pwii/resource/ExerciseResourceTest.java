package io.pwii.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import io.pwii.entity.Exercise;
import io.pwii.entity.Workout;
import io.pwii.entity.enums.WorkoutCategory;
import io.pwii.model.PageModel;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.model.request.ExerciseUpdateRequestModel;
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

  Gson gson = new Gson();

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

  Exercise exerciseEntityTwo = Exercise.builder()
      .title("cross over")
      .reps(10)
      .sets(3)
      .weight(15.0)
      .category(WorkoutCategory.CHEST)
      .workout(Workout.builder().id(2L).build())
      .createdAt(LocalDate.now())
      .updatedAt(LocalDateTime.now())
      .build();

  ExerciseRestModel exerciseRestModelOne = ExerciseRestModel.builder()
      .title("bench press")
      .reps(10)
      .sets(3)
      .weight(20.0)
      .category(WorkoutCategory.CHEST)
      .workoutId(1L)
      .createdAt(LocalDate.now())
      .updatedAt(exerciseEntityOne.getUpdatedAt())
      .build();

  ExerciseRestModel exerciseRestModelTwo = ExerciseRestModel.builder()
      .title("bench press")
      .reps(10)
      .sets(3)
      .weight(20.0)
      .category(WorkoutCategory.CHEST)
      .workoutId(1L)
      .createdAt(LocalDate.now())
      .updatedAt(exerciseEntityOne.getUpdatedAt())
      .build();

  ExerciseUpdateRequestModel exerciseUpdateModel = ExerciseUpdateRequestModel.builder()
      .sets(4)
      .reps(12)
      .build();


  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldCreateExercise() {
    when(this.exerciseService.create(exerciseModelOne)).thenReturn(exerciseEntityOne);

    ExerciseRestModel result = given()
        .contentType(ContentType.JSON)
        .body(exerciseModelOne)
        .when()
        .post().then().statusCode(201)
        .extract()
        .as(ExerciseRestModel.class);

    assertEquals(exerciseRestModelOne, result);
  }

  @Test
  public void shouldNotCreateExerciseWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .body(exerciseModelOne)
        .when()
        .post().then().statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldListExercises() {
    PageModel<Exercise> entityExercisesPageModel = PageModel.<Exercise>builder()
        .content(Arrays.asList(exerciseEntityOne, exerciseEntityTwo))
        .currentPage(0)
        .currentPageTotalItems(2)
        .numberOfPages(1)
        .totalItems(2L)
        .build();

    when(this.exerciseService.list(0, 25)).thenReturn(entityExercisesPageModel);

    PageModel<?> result = given()
        .contentType(ContentType.JSON)
        .params("page", "0", "limit", "25")
        .when()
        .get()
        .then()
        .statusCode(200)
        .extract()
        .as(PageModel.class);

    assertThat(result.getContent(), hasSize(2));
  }

  @Test
  public void shouldNotListExercisesWhenNotAuthenticated() {

    given()
        .contentType(ContentType.JSON)
        .params("page", "0", "limit", "25")
        .when()
        .get()
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldUpdateAnExerciseById() {
    Exercise exerciseEntity = gson.fromJson(gson.toJson(exerciseEntityOne), Exercise.class);
    exerciseEntity.setReps(12);
    exerciseEntity.setSets(4);

    ExerciseRestModel exerciseRest =
        gson.fromJson(gson.toJson(exerciseRestModelOne), ExerciseRestModel.class);
    exerciseRest.setReps(12);
    exerciseRest.setSets(4);

    when(this.exerciseService.update(1L, exerciseUpdateModel)).thenReturn(exerciseEntity);

    ExerciseRestModel result = given()
        .contentType(ContentType.JSON)
        .body(exerciseUpdateModel)
        .when()
        .put("/{exerciseId}", 1)
        .then()
        .statusCode(200)
        .extract()
        .as(ExerciseRestModel.class);

    assertEquals(exerciseRest, result);
  }

  @Test
  public void shouldNotUpdateAnExerciseWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .body(exerciseModelOne)
        .when()
        .put("/{exerciceId}", 1)
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldDeleteAnExerciseById() {
    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{exerciseId}", 1L)
        .then()
        .statusCode(200);
  }

  @Test
  public void shouldNotDeleteWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{exerciseId}", 1L)
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldThrowErrorWhenExerciseIdIsInvalid() {
    Long nonExistentId = 5489548L;
    doThrow(BadRequestException.class).when(this.exerciseService).delete(nonExistentId);

    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{exerciseId}", nonExistentId)
        .then()
        .statusCode(400);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldGetExerciseById() {
    when(this.exerciseService.getById(1L)).thenReturn(exerciseEntityOne);

    ExerciseRestModel result = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/{exerciseId}", 1)
        .then()
        .statusCode(200)
        .extract()
        .as(ExerciseRestModel.class);

    assertEquals(exerciseRestModelOne, result);
  }

  @Test
  public void shouldNotGetExerciseByIdWhenNotAuthenticated() {

    given()
        .contentType(ContentType.JSON)
        .when()
        .get("/{exerciseId}", 1)
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldThrowNotFoundforNonExistentId() {
    Long nonExistentId = 3893823L;

    when(this.exerciseService.getById(nonExistentId)).thenThrow(NotFoundException.class);

    given()
        .contentType(ContentType.JSON)
        .when()
        .get("/{exerciseId}", nonExistentId)
        .then()
        .statusCode(404);
  }

}
