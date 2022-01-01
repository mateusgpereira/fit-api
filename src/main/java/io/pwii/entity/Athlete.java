package io.pwii.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import io.pwii.entity.enums.UserRoles;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")
public class Athlete extends User {
  
  @Column(length = 5)
  private Integer height;

  @Column(precision = 6, scale = 3)
  private Double weight;

  
  @Builder
  public Athlete(Long id, String name, String email, Integer age, String password, String phone,
      String cpf, UserRoles role, LocalDate createdAt, LocalDateTime updatedAt, Integer height,
      Double weight) {
    super(id, name, email, age, password, phone, cpf, role, createdAt, updatedAt);
    this.height = height;
    this.weight = weight;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (getClass() != obj.getClass())
      return false;
    
    return super.equals(obj);
  }

}
