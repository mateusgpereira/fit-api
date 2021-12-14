package io.pwii.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Athlete extends User {
  
  @Column(length = 5)
  private Integer height;

  @Column(precision = 6, scale = 3)
  private Double weight;

  @ManyToOne(fetch = FetchType.LAZY)
  private Instructor instructor;

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
