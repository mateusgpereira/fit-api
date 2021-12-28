package io.pwii.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.pwii.entity.Instructor;
import io.pwii.model.request.InstructorRequestModel;
import io.pwii.service.impl.InstructorServiceImpl;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;

@QuarkusTest
public class InstructorResourceTest {

  @TestHTTPResource("/v1/instructors")
  URL instructorEndpoint;

  @InjectMock
  InstructorServiceImpl instructorService;

  private InstructorRequestModel requestModel = InstructorRequestModel.builder()
      .name("Test")
      .cpf("20387648089")
      .email("mateus@test.com")
      .password("123rty")
      .phone("51991299483")
      .age(28)
      .build();

  private Instructor entity = Instructor.builder()
      .id(1L)
      .name("Test")
      .cpf("20387648089")
      .email("mateus@test.com")
      .password("123rty")
      .phone("51991299483")
      .age(28)
      .createdAt(LocalDate.now())
      .updatedAt(LocalDateTime.now())
      .build();

  @BeforeEach
  void setUp() {
    when(instructorService.create(requestModel)).thenReturn(entity);
  }


  @Test
  public void shouldNotCreateInstructorWhenRequiredFieldsAreMissing() {
    given()
        .contentType(ContentType.JSON)
        .body(new InstructorRequestModel())
        .when().post(instructorEndpoint)
        .then()
        .statusCode(400);
  }

  @Test
  public void shouldCreateInstructor() {
    given()
        .contentType(ContentType.JSON)
        .body(requestModel)
        .when().post(instructorEndpoint)
        .then()
        .statusCode(201)
        .body("size()", is(8))
        .body("email", is("mateus@test.com"));
  }

}
