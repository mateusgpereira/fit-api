package io.pwii.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gym_class")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
  private Set<Athlete> athletes = new HashSet<>();

  @Column(length = 5)
  private Integer reservedPlaces;

  @CreationTimestamp
  private LocalDate createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public void setAthletes(Set<Athlete> athletes) {
    this.athletes = athletes;
  }

  public void setAthletes(List<Athlete> athletes) {
    this.setAthletes(new HashSet<>(athletes));
  }

  public void addToAthletes(Athlete athlete) {
    this.athletes.add(athlete);
  }

  public void addToAthletes(List<Athlete> athletes) {
    this.athletes.addAll(athletes);
  }

  public void removeFromAthlete(Athlete athlete){
    this.athletes.remove(athlete);
  }

  public void removeFromAthlete(List<Athlete> athletes) {
    this.athletes.removeAll(athletes);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    GymClass other = (GymClass) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }
}
