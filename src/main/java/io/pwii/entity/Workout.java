package io.pwii.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.pwii.entity.enums.WorkoutCategory;
import io.pwii.entity.enums.WorkoutCode;
import lombok.Data;

@Entity
@Data
public class Workout {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 1, nullable = false)
  @Enumerated(EnumType.STRING)
  private WorkoutCode code;

  @Column(length = 20, nullable = false)
  @Enumerated(EnumType.STRING)
  private WorkoutCategory category;

  @ManyToOne(fetch = FetchType.LAZY)
  private Instructor instructor;

  @ManyToOne(fetch = FetchType.LAZY)
  private Athlete athlete;

  @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
  private List<Exercise> exercises;

  @CreationTimestamp
  private LocalDate createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
  
}
