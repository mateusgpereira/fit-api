package io.pwii.resource;

import static io.restassured.RestAssured.given;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.junit.jupiter.api.Test;
import io.pwii.resource.impl.AthleteGymClassAppointmentResourceImpl;
import io.pwii.service.AthleteGymClassAppointmentService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(AthleteGymClassAppointmentResourceImpl.class)
public class AthleteGymClassAppointmentResourceTest {

  @InjectMock
  AthleteGymClassAppointmentService athleteGymClassAppointmentService;

  @InjectMock
  JsonWebToken jwt;
  
  @Test
  @TestSecurity(user = "alice@test.com", roles = "ATHLETE")
  public void shouldCreateAppointmentGymClass() {
    given()
      .contentType(ContentType.JSON)
      .when()
      .post("/{gymClassId}", 1, 1)
      .then()
      .statusCode(HttpStatus.SC_OK);
  }

  @Test
  public void shouldNotCreateAppointmentGymClassWhenNotAuthenticated() {
    given()
      .contentType(ContentType.JSON)
      .when()
      .post("/{gymClassId}", 1, 1)
      .then()
      .statusCode(HttpStatus.SC_UNAUTHORIZED);
  }

  @Test
  @TestSecurity(user = "alice@test.com", roles = "ATHLETE")
  public void shouldDeleteAnAppointmentGymClass() {
    given()
      .contentType(ContentType.JSON)
      .when()
      .delete("/{gymClassId}", 1, 1)
      .then()
      .statusCode(HttpStatus.SC_OK);
  }

  @Test
  public void shouldNotDeleteAppointmentGymClassWhenNotAuthenticated() {
    given()
      .contentType(ContentType.JSON)
      .when()
      .delete("/{gymClassId}", 1, 1)
      .then()
      .statusCode(HttpStatus.SC_UNAUTHORIZED);
  }
  
}
