package io.pwii.resource;

import static org.mockito.Mockito.when;
import javax.ws.rs.BadRequestException;
import org.apache.http.HttpStatus;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import io.pwii.model.request.UserLoginRequest;
import io.pwii.model.response.LoginResponse;
import io.pwii.resource.fixtures.UserLoginData;
import io.pwii.resource.impl.UserResourceImpl;
import io.pwii.service.UserService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(UserResourceImpl.class)
public class UserResourceTest {

  @InjectMock
  private UserService userService;

  @Test
  public void shouldLoginSuccessFully() {
    UserLoginRequest requestData = UserLoginData.createUserLoginRequest();
    LoginResponse expected = LoginResponse.builder()
        .message("Login Successful")
        .token("AWESOME_TOKEN")
        .build();

    when(this.userService.login(requestData)).thenReturn("AWESOME_TOKEN");

    LoginResponse actual = given()
        .contentType(ContentType.JSON)
        .body(requestData)
        .when()
        .post()
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .as(LoginResponse.class);

    assertEquals(expected, actual);
  }

  @Test
  public void shouldReturnErrorWhenCredentialsAreInvalid() {
    UserLoginRequest requestData = UserLoginData.createUserLoginRequest();
    when(this.userService.login(requestData)).thenThrow(BadRequestException.class);

    given()
        .contentType(ContentType.JSON)
        .body(requestData)
        .when()
        .post()
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

}
