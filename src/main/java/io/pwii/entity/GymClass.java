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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gym_class")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

  @Builder.Default
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

  public boolean removeFromAthlete(Athlete athlete) {
    return this.athletes.remove(athlete);
  }

  public void removeFromAthlete(List<Athlete> athletes) {
    this.athletes.removeAll(athletes);
  }

  public boolean removeAtlheteById(Long athleteId) {
    Athlete athlete = this.athletes.stream()
        .filter(item -> item.getId().equals(athleteId))
        .findFirst()
        .orElse(null);

    if (athlete == null) {
      return false;
    }

    return this.removeFromAthlete(athlete);
  }
}
