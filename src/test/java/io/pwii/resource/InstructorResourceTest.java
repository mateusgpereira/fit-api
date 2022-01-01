package io.pwii.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.pwii.entity.Instructor;
import io.pwii.model.PageModel;
import io.pwii.model.request.InstructorRequestModel;
import io.pwii.model.request.InstructorUpdateRequestModel;
import io.pwii.model.response.InstructorRestModel;
import io.pwii.resource.impl.InstructorResourceImpl;
import io.pwii.service.impl.InstructorServiceImpl;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(InstructorResourceImpl.class)
public class InstructorResourceTest {

  @InjectMock
  InstructorServiceImpl instructorService;

  Gson gson = new Gson();

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

  @Test
  public void shouldNotCreateInstructorWhenRequiredFieldsAreMissing() {
    given()
        .contentType(ContentType.JSON)
        .body(new InstructorRequestModel())
        .when().post()
        .then()
        .statusCode(400);
  }

  @Test
  public void shouldCreateInstructor() {
    when(instructorService.create(johnRequestModel)).thenReturn(johnEntity);

    InstructorRestModel result = given()
        .contentType(ContentType.JSON)
        .body(johnRequestModel)
        .when().post()
        .then()
        .statusCode(201)
        .body("size()", is(8))
        .extract().as(InstructorRestModel.class);

    assertThat(result, is(equalTo(johnRestModel)));
  }

  @Test
  @TestSecurity(user = "marie@test.com", roles = {"INSTRUCTOR", "ATHLETE"})
  public void shouldListInstructors() {
    when(instructorService.list(0, 25)).thenReturn(PageModel.<Instructor>builder()
        .content(Arrays.asList(johnEntity, marieEntity))
        .currentPage(0)
        .currentPageTotalItems(2)
        .numberOfPages(1)
        .totalItems(2L)
        .build());

    PageModel<?> result = given()
        .contentType(ContentType.JSON)
        .params("page", "0", "limit", "25")
        .when().get()
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
        .when().get()
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "jonh@test.com", roles = {"INSTRUCTOR", "ATHLETE"})
  public void shouldUpdateInstructorPhoneAndName() {
    InstructorUpdateRequestModel updateRequestModel = new InstructorUpdateRequestModel();
    updateRequestModel.setName("John Smith");
    updateRequestModel.setPhone("351991563248");

    Instructor updatedEntity = gson.fromJson(gson.toJson(johnEntity), Instructor.class);
    updatedEntity.setName("John Smith");
    updatedEntity.setPhone("351991563248");

    when(instructorService.update(1L, updateRequestModel)).thenReturn(updatedEntity);

    InstructorRestModel expectedModel =
        gson.fromJson(gson.toJson(johnRestModel), InstructorRestModel.class);
    expectedModel.setName("John Smith");
    expectedModel.setPhone("351991563248");

    InstructorRestModel result = given()
        .contentType(ContentType.JSON)
        .body(updateRequestModel)
        .when().put("/{instructorId}", 1)
        .then()
        .statusCode(200)
        .body("size()", is(8))
        .extract()
        .as(InstructorRestModel.class);

    assertThat(result, equalTo(expectedModel));
  }

  @Test
  public void shouldNotUpdateWhenNotAuthenticated() {
    InstructorUpdateRequestModel updateRequestModel = new InstructorUpdateRequestModel();
    updateRequestModel.setName("John Smith");
    updateRequestModel.setPhone("351991563248");

    given()
        .contentType(ContentType.JSON)
        .body(updateRequestModel)
        .when().put("/{instructorId}", 1)
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "jonh@test.com", roles = {"INSTRUCTOR", "ATHLETE"})
  public void shouldDeleteInstructorById() {
    given()
        .contentType(ContentType.JSON)
        .when().delete("/{instructorId}", 1)
        .then()
        .statusCode(200);
  }

  @Test
  public void shouldNotDeleteWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .when().delete("/{instructorId}", 1)
        .then()
        .statusCode(401);
  }

  @Test
  @TestSecurity(user = "john@test.com", roles = {"INSTRUCTOR", "ATHLETE"})
  public void shouldGetUserById() {
    when(instructorService.getById(1L)).thenReturn(johnEntity);

    InstructorRestModel result = given()
        .contentType(ContentType.JSON)
        .when().get("/{instructorId}", 1)
        .then()
        .statusCode(200)
        .extract()
        .as(InstructorRestModel.class);

    assertThat(result, equalTo(johnRestModel));
  }

  @Test
  public void shouldNotGetByIdWhenNotAuthenticated() {
    given()
        .contentType(ContentType.JSON)
        .when().get("/{instructorId}", 1)
        .then()
        .statusCode(401);
  }

}
