package io.pwii.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InstructorUpdateRequest {

  @Size(min = 3, max = 50)
  private String name;

  @Email
  private String email;

  @Min(value = 16)
  private short age;

  private String password;

  @Size(min = 8, max = 20)
  private String phone;
  
}
