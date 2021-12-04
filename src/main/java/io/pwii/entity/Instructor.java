package io.pwii.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Instructor extends User {

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "instructor")
  private List<Athlete> athletes;

}