package io.pwii.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;
import io.pwii.entity.Athlete;
import io.pwii.entity.Exercise;
import io.pwii.entity.Instructor;
import io.pwii.entity.Workout;
import io.pwii.entity.enums.WorkoutCategory;
import io.pwii.entity.enums.WorkoutCode;
import io.pwii.model.PageModel;
import io.pwii.model.enums.UpdateOperations;
import io.pwii.model.request.ExerciseRequestModel;
import io.pwii.model.request.WorkoutExerciseUpdateRequestModel;
import io.pwii.model.request.WorkoutRequestModel;
import io.pwii.model.request.WorkoutUpdateRequestModel;
import io.pwii.model.response.ExerciseRestModel;
import io.pwii.model.response.WorkoutRestModel;
import io.pwii.resource.impl.WorkoutResourceImpl;
import io.pwii.service.WorkoutService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(WorkoutResourceImpl.class)
public class WorkoutResourceTest {

  Gson gson = new Gson();

  @InjectMock
  private WorkoutService workoutService;

  private WorkoutRequestModel workoutRequestModelOne = WorkoutRequestModel.builder()
      .athleteId(1L)
      .category(WorkoutCategory.CHEST)
      .code(WorkoutCode.A)
      .instructorId(10L)
      .exercises(null)
      .build();

  private Athlete athlete = Athlete.builder().id(1L).build();
  private Instructor instructor = Instructor.builder().id(10L).build();


  private Workout workoutEntityOne = Workout.builder()
      .id(1L)
      .athlete(athlete)
      .category(WorkoutCategory.CHEST)
      .code(WorkoutCode.A)
      .instructor(instructor)
      .exercises(null)
      .createdAt(LocalDate.now())
      .updatedAt(LocalDateTime.now())
      .build();

  private Workout workoutEntityTwo = Workout.builder()
      .id(2L)
      .athlete(athlete)
      .category(WorkoutCategory.BACK)
      .code(WorkoutCode.B)
      .instructor(instructor)
      .exercises(null)
      .createdAt(LocalDate.now())
      .updatedAt(LocalDateTime.now())
      .build();

  private WorkoutRestModel workoutRestModelOne = WorkoutRestModel.builder()
      .id(1L)
      .athleteId(1L)
      .category(WorkoutCategory.CHEST)
      .code(WorkoutCode.A)
      .instructorId(10L)
      .exercises(null)
      .createdAt(LocalDate.now())
      .updatedAt(workoutEntityOne.getUpdatedAt())
      .build();

  private WorkoutUpdateRequestModel workoutUpdateRequestModel = WorkoutUpdateRequestModel.builder()
      .category(WorkoutCategory.ABDOMINALS)
      .code(WorkoutCode.E)
      .build();

  private ExerciseRequestModel exerciseRequestModel = ExerciseRequestModel.builder()
      .category(WorkoutCategory.CHEST)
      .reps(12)
      .sets(3)
      .weight(25.0)
      .build();


  private Exercise exerciseEntityOne = Exercise.builder()
      .id(1L)
      .category(WorkoutCategory.CHEST)
      .reps(12)
      .sets(3)
      .weight(25.0)
      .createdAt(LocalDate.now())
      .updatedAt(LocalDateTime.now())
      .build();

  private ExerciseRestModel exerciseRest = ExerciseRestModel.builder()
      .id(1L)
      .category(WorkoutCategory.CHEST)
      .reps(12)
      .sets(3)
      .weight(25.0)
      .createdAt(LocalDate.now())
      .updatedAt(exerciseEntityOne.getUpdatedAt())
      .build();


  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldCreateWorkoutSuccessfully() {
    when(workoutService.create(workoutRequestModelOne)).thenReturn(workoutEntityOne);

    WorkoutRestModel result = given()
        .contentType(ContentType.JSON)
        .body(workoutRequestModelOne)
        .when()
        .post()
        .then()
        .statusCode(201)
        .extract()
        .as(WorkoutRestModel.class);

    assertEquals(workoutRestModelOne, result);
  }

  @Test
  public void shouldNotCreateWorkoutWhenNotAuthenticated() {

    given()
        .contentType(ContentType.JSON)
        .body(workoutRequestModelOne)
        .when()
        .post()
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldListWorkouts() {
    when(workoutService.list(0, 25)).thenReturn(PageModel.<Workout>builder()
        .content(Arrays.asList(workoutEntityOne, workoutEntityTwo))
        .currentPage(0)
        .currentPageTotalItems(2)
        .numberOfPages(1)
        .totalItems(2L)
        .build());

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
  public void shouldNotListWorkoutsWhenNotAuthenticated() {
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
  public void shouldUpdateAWorkoutById() {
    Workout updatedEntity = gson.fromJson(gson.toJson(workoutEntityOne), Workout.class);
    updatedEntity.setCategory(WorkoutCategory.ABDOMINALS);
    updatedEntity.setCode(WorkoutCode.E);

    WorkoutRestModel expected =
        gson.fromJson(gson.toJson(workoutRestModelOne), WorkoutRestModel.class);
    expected.setCategory(WorkoutCategory.ABDOMINALS);
    expected.setExercises(Collections.emptySet());
    expected.setCode(WorkoutCode.E);

    when(this.workoutService.update(1L, workoutUpdateRequestModel)).thenReturn(updatedEntity);

    WorkoutRestModel result = given()
        .contentType(ContentType.JSON)
        .body(workoutUpdateRequestModel)
        .when()
        .put("/{workoutId}", 1)
        .then()
        .statusCode(200)
        .extract()
        .as(WorkoutRestModel.class);

    assertEquals(expected, result);
  }

  @Test
  public void shouldNotUpdateWorkoutWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .body(workoutUpdateRequestModel)
        .when()
        .put("/{workoutId}", 1)
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldDeleteWorkoutById() {
    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{workoutId}", 1)
        .then()
        .statusCode(200);
  }

  @Test
  public void shouldNotDeleteWorkoutByIdWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{workoutId}", 1)
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldGetWorkoutById() {
    when(workoutService.getById(1L)).thenReturn(workoutEntityOne);

    WorkoutRestModel actual = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/{workoutId}", 1)
        .then()
        .statusCode(200)
        .extract()
        .as(WorkoutRestModel.class);

    assertEquals(workoutRestModelOne, actual);
  }

  @Test
  public void shouldNotGetWorkoutByIdWhenNotAuthenticated() {

    given()
        .contentType(ContentType.JSON)
        .when()
        .get("/{workoutId}", 1)
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldAddExerciseToWorkout() {
    WorkoutExerciseUpdateRequestModel workoutExerciseUpdate =
        WorkoutExerciseUpdateRequestModel.builder()
            .operation(UpdateOperations.ADD)
            .values(Arrays.asList(exerciseRequestModel))
            .build();

    List<WorkoutExerciseUpdateRequestModel> data = new ArrayList<>();
    data.add(workoutExerciseUpdate);

    Workout updatedEntity = gson.fromJson(gson.toJson(workoutEntityOne), Workout.class);
    updatedEntity.addToExercises(exerciseEntityOne);

    WorkoutRestModel expected =
        gson.fromJson(gson.toJson(workoutRestModelOne), WorkoutRestModel.class);
    expected.setExercises(Sets.newSet(exerciseRest));


    when(this.workoutService.updateExercises(1L, data)).thenReturn(updatedEntity);

    WorkoutRestModel actual = given()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .patch("/{workoutId}/exercises", 1)
        .then()
        .statusCode(200)
        .extract()
        .as(WorkoutRestModel.class);

    assertEquals(expected, actual);
  }

  @Test
  public void shouldNotUpdateExerciseWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .body(new ArrayList<WorkoutExerciseUpdateRequestModel>())
        .when()
        .patch("/{workoutId}/exercises", 1)
        .then()
        .statusCode(401);

  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldRemoveExerciseFromWorkout() {
    WorkoutExerciseUpdateRequestModel workoutExerciseUpdate =
        WorkoutExerciseUpdateRequestModel.builder()
            .operation(UpdateOperations.REMOVE)
            .ids(Arrays.asList(1L))
            .build();

    List<WorkoutExerciseUpdateRequestModel> data = new ArrayList<>();
    data.add(workoutExerciseUpdate);

    when(this.workoutService.updateExercises(1L, data)).thenReturn(workoutEntityOne);

    WorkoutRestModel actual = given()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .patch("/{workoutId}/exercises", 1)
        .then()
        .statusCode(200)
        .extract()
        .as(WorkoutRestModel.class);

    assertEquals(workoutRestModelOne, actual);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldRemoveExerciseFromWorkoutById() {

    when(this.workoutService.removeExercise(1L, 2L)).thenReturn(workoutEntityOne);

    WorkoutRestModel actual = given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{workoutId}/exercises/{exerciseId}", 1, 2)
        .then()
        .statusCode(200)
        .extract()
        .as(WorkoutRestModel.class);

    assertEquals(workoutRestModelOne, actual);
  }

  @Test
  public void shouldNotRemoveExerciseByIdWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{workoutId}/exercises/{exerciseId}", 1, 2)
        .then()
        .statusCode(401);
  }
}
