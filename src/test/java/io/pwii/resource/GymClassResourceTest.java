package io.pwii.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.ws.rs.BadRequestException;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import io.pwii.entity.GymClass;
import io.pwii.model.PageModel;
import io.pwii.model.enums.UpdateOperations;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.request.GymClassUpdateRequestModel;
import io.pwii.model.request.UpdateRequestModel;
import io.pwii.model.response.GymClassRestModel;
import io.pwii.resource.fixtures.AthleteData;
import io.pwii.resource.fixtures.GymClassData;
import io.pwii.resource.impl.GymClassResourceImpl;
import io.pwii.service.GymClassService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(GymClassResourceImpl.class)
public class GymClassResourceTest {

  @InjectMock
  private GymClassService gymClassService;

  @Test
  @TestSecurity(user = "marie@test.com", roles = "INSTRUCTOR")
  public void shouldCreateAGymClass() {
    GymClassRequestModel gymClassRequestModel = GymClassData.getGymClassRequestModel();
    GymClass gymClassEntity = GymClassData.getGymClassEntity(1L, "Yoga", "john", 20);
    GymClassRestModel expected = GymClassData.getGymClassRestModel(1L, "Yoga", "john", 20);

    when(this.gymClassService.create(gymClassRequestModel)).thenReturn(gymClassEntity);

    GymClassRestModel actual = given()
        .contentType(ContentType.JSON)
        .body(gymClassRequestModel)
        .when()
        .post()
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .as(GymClassRestModel.class);

    assertEquals(expected, actual);
  }

  @Test
  public void shouldNotCreateAGymClassWhenNotAuthenticated() {
    GymClassRequestModel gymClassRequestModel = GymClassData.getGymClassRequestModel();

    given()
        .contentType(ContentType.JSON)
        .body(gymClassRequestModel)
        .when()
        .post()
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = "INSTRUCTOR")
  public void shouldListGymClassesAsInstructor() {

    when(this.gymClassService.list(0, 25)).thenReturn(GymClassData.getGymClassPage());

    PageModel<?> actual = given()
        .contentType(ContentType.JSON)
        .params("page", "0", "limit", "25")
        .when()
        .get()
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .as(PageModel.class);

    assertThat(actual.getContent(), hasSize(2));
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = "ATHLETE")
  public void shouldListGymClassesAsAthlete() {

    when(this.gymClassService.list(0, 25)).thenReturn(GymClassData.getGymClassPage());

    PageModel<?> actual = given()
        .contentType(ContentType.JSON)
        .params("page", "0", "limit", "25")
        .when()
        .get()
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .as(PageModel.class);

    assertThat(actual.getContent(), hasSize(2));
  }

  @Test
  public void shouldNotListGymClassesWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .params("page", "0", "limit", "25")
        .when()
        .get()
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = "INSTRUCTOR")
  public void shouldUpdateGymClassById() {
    GymClassUpdateRequestModel requestBody = GymClassData.getGymClassUpdateModel("New Title", 50);
    GymClassRestModel expected = GymClassData.getGymClassRestModel(1L, "New Title", "john", 50);

    when(this.gymClassService.update(1L, requestBody))
        .thenReturn(GymClassData.getGymClassEntity(1L, "New Title", "john", 50));

    GymClassRestModel actual = given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .put("/{gymClassId}", 1)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .as(GymClassRestModel.class);

    assertEquals(expected, actual);
  }

  @Test
  public void shouldNotUpdateGymClassByIdWhenNotAuthenticated() {
    GymClassUpdateRequestModel requestBody = GymClassData.getGymClassUpdateModel("New Title", 50);

    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .put("/{gymClassId}", 1)
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = "INSTRUCTOR")
  public void shouldDeleteGymClassById() {
    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{gymClassId}", 1)
        .then()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = "INSTRUCTOR")
  public void shouldNotDeleteWhenIdIsInvalid() {
    Long invalidId = 568978L;
    doThrow(BadRequestException.class).when(this.gymClassService).delete(invalidId);

    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{gymClassId}", invalidId)
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  public void shouldNotDeleteWhenNotAuthenticated() {

    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{gymClassId}", 2)
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = "INSTRUCTOR")
  public void shouldGetGymClassBydId() {
    GymClassRestModel expected = GymClassData.getGymClassRestModel(1L, "Yoga", "marie", 20);
    when(this.gymClassService.getById(1L))
        .thenReturn(GymClassData.getGymClassEntity(1L, "Yoga", "marie", 20));

    GymClassRestModel actual = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/{gymClassId}", 1)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .as(GymClassRestModel.class);

    assertEquals(expected, actual);
  }

  @Test
  public void shouldNotGetGymClassBydIdWhenNotAuthenticated() {

    given()
        .contentType(ContentType.JSON)
        .when()
        .get("/{gymClassId}", 1)
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = "INSTRUCTOR")
  public void shouldUpdateGymClassAthletes() {
    List<UpdateRequestModel<Long>> requestBody =
        Arrays.asList(new UpdateRequestModel<>(UpdateOperations.ADD, Arrays.asList(1L, 2L)));

    GymClass stubEntity = GymClassData.getGymClassEntity(1L, "Yoga", "marie", 20);
    stubEntity.addToAthletes(Arrays.asList(AthleteData.getAthleteEntity(1L, "alice"),
        AthleteData.getAthleteEntity(2L, "katy")));

    GymClassRestModel expected = GymClassData.getGymClassRestModel(1L, "Yoga", "marie", 20);
    expected.setAthletes(new HashSet<>(
        Arrays.asList(
            AthleteData.getAthleteRestBriefModel(1L, "alice"),
            AthleteData.getAthleteRestBriefModel(2L, "katy"))));

    when(this.gymClassService.updateAthletes(1L, requestBody)).thenReturn(stubEntity);

    GymClassRestModel actual = given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .patch("/{gymclassId}/athletes", 1L)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .as(GymClassRestModel.class);

    assertEquals(expected, actual);
  }

  @Test
  public void shouldNotUpdateGymClassAthletesWhenNotAuthenticated() {
    List<UpdateRequestModel<Long>> requestBody =
        Arrays.asList(new UpdateRequestModel<>(UpdateOperations.ADD, Arrays.asList(1L, 2L)));

    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .patch("/{gymclassId}/athletes", 1L)
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED);
  }

  @Test
  @TestSecurity(user = "alice@test.com", roles = "ATHLETE")
  public void shouldNotUpdateGymClassAthletesWhenRoleIsAThlete() {
    List<UpdateRequestModel<Long>> requestBody =
        Arrays.asList(new UpdateRequestModel<>(UpdateOperations.ADD, Arrays.asList(1L, 2L)));

    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .patch("/{gymclassId}/athletes", 1L)
        .then()
        .statusCode(HttpStatus.SC_FORBIDDEN);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = "ATHLETE")
  public void shouldAddAnAthleteToAGymClass() {
    GymClass stubEntity = GymClassData.getGymClassEntity(1L, "Yoga", "marie", 20);
    stubEntity.addToAthletes(Arrays.asList(AthleteData.getAthleteEntity(1L, "alice")));

    GymClassRestModel expected = GymClassData.getGymClassRestModel(1L, "Yoga", "marie", 20);
    expected.setAthletes(
        new HashSet<>(Arrays.asList(AthleteData.getAthleteRestBriefModel(1L, "alice"))));

    when(this.gymClassService.addAthlete(1L, 1L)).thenReturn(stubEntity);

    GymClassRestModel actual = given()
        .contentType(ContentType.JSON)
        .when()
        .patch("/{gymClassId}/athletes/{athleteId}", 1, 1)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .as(GymClassRestModel.class);

    assertEquals(expected, actual);
  }

  @Test
  public void shouldNotAddAnAthleteToAGymClassWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .when()
        .patch("/{gymClassId}/athletes/{athleteId}", 1, 1)
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = "ATHLETE")
  public void shouldRemoveAnAthleteFromAGymClass() {
    GymClass stubEntity = GymClassData.getGymClassEntity(1L, "Yoga", "marie", 20);
    stubEntity.addToAthletes(Arrays.asList(AthleteData.getAthleteEntity(2L, "katy")));

    GymClassRestModel expected = GymClassData.getGymClassRestModel(1L, "Yoga", "marie", 20);
    expected.setAthletes(
        new HashSet<>(Arrays.asList(AthleteData.getAthleteRestBriefModel(2L, "katy"))));

    when(this.gymClassService.deleteAthlete(1L, 1L)).thenReturn(stubEntity);

    GymClassRestModel actual = given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{gymClassId}/athletes/{athleteId}", 1, 1)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .as(GymClassRestModel.class);

    assertEquals(expected, actual);
  }

  @Test
  public void shouldNotRemovenAthleteFromAGymClassWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{gymClassId}/athletes/{athleteId}", 1, 1)
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED);
  }

}
