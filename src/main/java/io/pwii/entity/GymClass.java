package io.pwii.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.pwii.entity.enums.GymClassCategory;
import lombok.Data;

@Entity
@Table(name = "gym_class")
@Data
public class GymClass {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(nullable = false, length = 30)
  @Enumerated(EnumType.STRING)
  private GymClassCategory type;

  @Column(columnDefinition = "DATE", nullable = false)
  private LocalDate classDate;

  @Column(columnDefinition = "TIME", nullable = false)
  private LocalTime classTime;

  @ManyToOne(optional = false)
  private Instructor instructor;

  @Column(length = 5)
  private Integer maxAthletes;

  @ManyToMany
  private List<Athlete> athletes;

  @Column(length = 5)
  private Integer reservedPlaces;

  @CreationTimestamp
  private LocalDate createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
  
}
