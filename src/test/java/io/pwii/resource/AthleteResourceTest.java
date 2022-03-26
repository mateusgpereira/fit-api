package io.pwii.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
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
import io.pwii.entity.Athlete;
import io.pwii.model.PageModel;
import io.pwii.model.request.AthleteRequestModel;
import io.pwii.model.request.AthleteUpdateRequest;
import io.pwii.model.response.AthleteRestModel;
import io.pwii.resource.fixtures.AthleteData;
import io.pwii.resource.impl.AthleteResourceImpl;
import io.pwii.service.impl.AthleteServiceImpl;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(AthleteResourceImpl.class)
public class AthleteResourceTest {

  @InjectMock
  AthleteServiceImpl athleteService;

  Gson gson = new Gson();

  AthleteRequestModel aliceRequestModel = AthleteRequestModel.builder()
      .name("alice")
      .age(21)
      .cpf("38555756065")
      .email("alice@test.com")
      .password("123rty")
      .build();

  Athlete aliceEntity = AthleteData.getAthleteEntity(3L, "alice");

  AthleteRestModel aliceRestModel = AthleteRestModel.builder()
      .id(3L)
      .name("alice")
      .age(21)
      .cpf("38555756065")
      .email("alice@test.com")
      .createdAt(LocalDate.now())
      .updatedAt(aliceEntity.getUpdatedAt())
      .build();

  Athlete katyEntity = AthleteData.getAthleteEntity(4L, "katy");

  AthleteRestModel katyRestModel = AthleteRestModel.builder()
      .id(4L)
      .name("katy")
      .age(21)
      .cpf("52453561049")
      .email("katy@test.com")
      .createdAt(LocalDate.now())
      .updatedAt(katyEntity.getUpdatedAt())
      .build();


  @Test
  public void shouldCreateAnAthlete() {
    when(athleteService.create(aliceRequestModel)).thenReturn(aliceEntity);

    AthleteRestModel result = given()
        .contentType(ContentType.JSON)
        .body(aliceRequestModel)
        .when().post()
        .then()
        .statusCode(201)
        .extract()
        .as(AthleteRestModel.class);

    assertEquals(aliceRestModel, result);
  }

  @Test
  public void shouldNotCreateAnAthleteWhenRequiredFieldIsNotProvided() {
    AthleteRequestModel invalidRequestModel =
        gson.fromJson(gson.toJson(aliceRequestModel), AthleteRequestModel.class);
    invalidRequestModel.setCpf(null);
    invalidRequestModel.setEmail(null);

    given()
        .contentType(ContentType.JSON)
        .body(invalidRequestModel)
        .when().post()
        .then()
        .statusCode(400);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldListAthletesSuccessfully() {
    PageModel<Athlete> entityAthletesPageModel = PageModel.<Athlete>builder()
        .content(Arrays.asList(aliceEntity, katyEntity))
        .currentPage(0)
        .currentPageTotalItems(2)
        .numberOfPages(1)
        .totalItems(2L)
        .build();

    when(athleteService.list(0, 10)).thenReturn(entityAthletesPageModel);

    PageModel<?> result = given()
        .contentType(ContentType.JSON)
        .params("page", "0", "limit", "10")
        .when()
        .get()
        .then()
        .statusCode(200)
        .body("size()", is(5))
        .extract()
        .as(PageModel.class);

    assertThat(result.getContent(), hasSize(2));
  }

  @Test
  public void shouldNotListAthletesWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .param("page", "0", "limit", "0")
        .when()
        .get()
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldUpdateAthleteEmail() {
    LocalDateTime updateTime = LocalDateTime.now();

    Athlete updatedEntity = gson.fromJson(gson.toJson(aliceEntity), Athlete.class);
    updatedEntity.setEmail("alice@new.com");
    updatedEntity.setUpdatedAt(updateTime);

    AthleteRestModel expectedAthleteModel =
        gson.fromJson(gson.toJson(aliceRestModel), AthleteRestModel.class);
    expectedAthleteModel.setEmail("alice@new.com");
    expectedAthleteModel.setUpdatedAt(updateTime);

    AthleteUpdateRequest updateAthleteModel = new AthleteUpdateRequest();
    updateAthleteModel.setEmail("alice@new.com");
    when(athleteService.update(3L, updateAthleteModel)).thenReturn(updatedEntity);

    AthleteRestModel result = given()
        .contentType(ContentType.JSON)
        .body(updateAthleteModel)
        .when()
        .put("/{athleteId}", 3)
        .then()
        .statusCode(200)
        .extract()
        .as(AthleteRestModel.class);

    assertEquals(expectedAthleteModel, result);
  }

  @Test
  public void shouldNotUpdatedWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .body(new AthleteUpdateRequest())
        .when()
        .put("/{athleteId}", 3)
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldDeleteAthleteSuccessfully() {
    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{athleteId}", 3)
        .then()
        .statusCode(200);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldThrowErrorWhenThereIsNoAthleteWithProvidedId() {
    Long nonExistentId = 8904L;
    doThrow(BadRequestException.class).when(athleteService).delete(nonExistentId);

    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{athleteId}", nonExistentId)
        .then()
        .statusCode(400);
  }

  @Test
  public void shouldNotDeleteWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/{athleteId}", 34343)
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldGetAthleteById() {
    when(athleteService.getById(3L)).thenReturn(aliceEntity);

    AthleteRestModel result = given()
        .contentType(ContentType.JSON)
        .when()
        .get("/{athleteId}", 3)
        .then()
        .statusCode(200)
        .extract()
        .as(AthleteRestModel.class);

    assertEquals(aliceRestModel, result);
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR"})
  public void shouldReturnNotFoundWhenAthleteIdDoesNotExists() {
    Long nonExistentId = 7987987L;
    when(athleteService.getById(nonExistentId)).thenThrow(NotFoundException.class);

    given()
        .contentType(ContentType.JSON)
        .when()
        .get("/{athleteId}", nonExistentId)
        .then()
        .statusCode(404);
  }

  @Test
  public void shouldNotGetByIdWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .when()
        .get("/{athleteId}", 3)
        .then()
        .statusCode(401);
  }


}
