package io.pwii.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.pwii.entity.enums.WorkoutCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

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

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL )
  private Workout workout;

  @CreationTimestamp
  private LocalDate createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((category == null) ? 0 : category.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((reps == null) ? 0 : reps.hashCode());
    result = prime * result + ((sets == null) ? 0 : sets.hashCode());
    result = prime * result + ((weight == null) ? 0 : weight.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Exercise other = (Exercise) obj;
    if (category != other.category)
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (reps == null) {
      if (other.reps != null)
        return false;
    } else if (!reps.equals(other.reps))
      return false;
    if (sets == null) {
      if (other.sets != null)
        return false;
    } else if (!sets.equals(other.sets))
      return false;
    if (weight == null) {
      if (other.weight != null)
        return false;
    } else if (!weight.equals(other.weight))
      return false;
    return true;
  }
}
