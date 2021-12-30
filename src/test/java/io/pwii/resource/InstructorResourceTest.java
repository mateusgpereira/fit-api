package io.pwii.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.pwii.entity.Instructor;
import io.pwii.model.PageModel;
import io.pwii.model.request.InstructorRequestModel;
import io.pwii.model.response.InstructorRestModel;
import io.pwii.service.impl.InstructorServiceImpl;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
public class InstructorResourceTest {

  @TestHTTPResource("/v1/instructors")
  URL instructorEndpoint;

  @InjectMock
  InstructorServiceImpl instructorService;

  private InstructorRequestModel johnRequestModel = InstructorRequestModel.builder()
      .name("John")
      .cpf("20387648089")
      .email("john@test.com")
      .password("123rty")
      .phone("51991299483")
      .age(28)
      .build();

  private Instructor johnEntity = Instructor.builder()
      .id(1L)
      .name("John")
      .cpf("20387648089")
      .email("john@test.com")
      .password("123rty")
      .phone("51991299483")
      .age(28)
      .createdAt(LocalDate.now())
      .updatedAt(LocalDateTime.now())
      .build();

  private Instructor marieEntity = Instructor.builder()
      .id(2L)
      .name("Marie")
      .cpf("38667293083")
      .email("marie@test.com")
      .password("123rty")
      .phone("51991299484")
      .age(25)
      .createdAt(LocalDate.now())
      .updatedAt(LocalDateTime.now())
      .build();

  private InstructorRestModel johnRestModel = InstructorRestModel.builder()
      .id(1L)
      .name("John")
      .cpf("20387648089")
      .email("john@test.com")
      .phone("51991299483")
      .age(28)
      .createdAt(johnEntity.getCreatedAt())
      .updatedAt(johnEntity.getUpdatedAt())
      .build();

  private InstructorRestModel marieRestModel = InstructorRestModel.builder()
      .id(2L)
      .name("Marie")
      .cpf("38667293083")
      .email("marie@test.com")
      .phone("51991299484")
      .age(25)
      .createdAt(marieEntity.getCreatedAt())
      .updatedAt(marieEntity.getUpdatedAt())
      .build();

  @BeforeEach
  void setUp() {
    when(instructorService.create(johnRequestModel)).thenReturn(johnEntity);
    when(instructorService.list(0, 25)).thenReturn(PageModel.<Instructor>builder()
        .content(Arrays.asList(johnEntity, marieEntity))
        .currentPage(0)
        .currentPageTotalItems(2)
        .numberOfPages(1)
        .totalItems(2L)
        .build());
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
    InstructorRestModel result = given()
        .contentType(ContentType.JSON)
        .body(johnRequestModel)
        .when().post(instructorEndpoint)
        .then()
        .statusCode(201)
        .body("size()", is(8))
        .extract().as(InstructorRestModel.class);

    assertThat(result, is(equalTo(johnRestModel)));
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR", "ATHLETE"})
  public void shouldListInstructors() {
    PageModel<InstructorRestModel> result = given()
        .contentType(ContentType.JSON)
        .params("page", "0", "limit", "25")
        .when().get(instructorEndpoint)
        .then()
        .statusCode(200)
        .body("size()", is(5))
        .extract()
        .as(PageModel.class);
    assertThat(result.getContent(), hasSize(2));
  }

  @Test
  public void shouldNotListInstructorsWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .when().get(instructorEndpoint)
        .then()
        .statusCode(401);
  }



}
