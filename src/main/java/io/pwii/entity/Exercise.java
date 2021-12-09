package io.pwii.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.pwii.entity.enums.WorkoutCategory;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

@Entity
@Data
public class Exercise extends PanacheEntityBase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50, nullable = false)
  private String title;

  @Column
  private String description;

  @Column(length = 4)
  private Integer reps;

  @Column(length = 2)
  private Integer sets;

  @Column(length = 10)
  private String time;

  @Column(length = 5)
  private Double weight;

  @Column(length = 50)
  private String equipment;

  @Column(length = 20, nullable = false)
  @Enumerated(EnumType.STRING)
  private WorkoutCategory category;

  @ManyToOne(fetch = FetchType.LAZY)
  private Workout workout;

  @CreationTimestamp
  private LocalDate createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
  
}
