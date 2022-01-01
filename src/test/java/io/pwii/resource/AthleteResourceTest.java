package io.pwii.resource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import io.pwii.entity.Athlete;
import io.pwii.model.request.AthleteRequestModel;
import io.pwii.model.response.AthleteRestModel;
import io.pwii.resource.impl.AthleteResourceImpl;
import io.pwii.service.impl.AthleteServiceImpl;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
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

  Athlete aliceEntity = Athlete.builder()
      .id(3L)
      .name("alice")
      .age(21)
      .cpf("38555756065")
      .email("alice@test.com")
      .password("123rty")
      .createdAt(LocalDate.now())
      .updatedAt(LocalDateTime.now())
      .build();

  AthleteRestModel aliceRestModel = AthleteRestModel.builder()
      .id(3L)
      .name("alice")
      .age(21)
      .cpf("38555756065")
      .email("alice@test.com")
      .createdAt(LocalDate.now())
      .updatedAt(aliceEntity.getUpdatedAt())
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

}
