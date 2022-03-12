package io.pwii.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.apache.http.HttpStatus;
import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Test;
import io.pwii.entity.GymClass;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.response.GymClassRestModel;
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
    GymClass gymClassEntity = GymClassData.getGymClassEntity();
    GymClassRestModel expected = GymClassData.getGymClassRestModel();

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

}
