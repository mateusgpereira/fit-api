package io.pwii.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Athlete extends User {
  
  @Column(length = 5)
  private Integer height;

  @Column(precision = 6, scale = 3)
  private Double weight;

  @ManyToOne(fetch = FetchType.LAZY)
  private Instructor instructor;
  
}
