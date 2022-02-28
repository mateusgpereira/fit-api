package io.pwii.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import io.pwii.entity.Athlete;
import io.pwii.entity.Instructor;
import io.pwii.entity.Workout;
import io.pwii.entity.enums.WorkoutCategory;
import io.pwii.entity.enums.WorkoutCode;
import io.pwii.model.PageModel;
import io.pwii.model.request.WorkoutRequestModel;
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

  @InjectMock
  private WorkoutService workoutService;

  private WorkoutRequestModel workoutRequestModelOne = WorkoutRequestModel.builder()
      .athleteId(1L)
      .category(WorkoutCategory.CHEST)
      .code(WorkoutCode.A)
      .instructorId(10L)
      .exercises(null)
      .build();

  private WorkoutRequestModel workoutRequestModelTwo = WorkoutRequestModel.builder()
      .athleteId(1L)
      .category(WorkoutCategory.BACK)
      .code(WorkoutCode.B)
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

  private WorkoutRestModel workoutRestModelTwo = WorkoutRestModel.builder()
      .id(2L)
      .athleteId(1L)
      .category(WorkoutCategory.BACK)
      .code(WorkoutCode.B)
      .instructorId(10L)
      .exercises(null)
      .createdAt(LocalDate.now())
      .updatedAt(workoutEntityOne.getUpdatedAt())
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
}
