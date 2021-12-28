package io.pwii.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import io.pwii.entity.enums.UserRoles;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("1")
public class Instructor extends User {

  @Builder
  public Instructor(Long id, String name, String email, Integer age, String password, String phone,
      String cpf, UserRoles role, LocalDate createdAt, LocalDateTime updatedAt) {
    super(id, name, email, age, password, phone, cpf, role, createdAt, updatedAt);
  }

}
