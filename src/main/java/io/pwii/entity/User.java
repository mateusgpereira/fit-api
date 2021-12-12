package io.pwii.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

@MappedSuperclass
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50, nullable = false)
  private String name;

  @Column(length = 50, nullable = false, unique = true)
  private String email;

  @Column(length = 3)
  private Integer age;

  @Column(nullable = false)
  private String password;

  @Column(length = 20, nullable = false)
  private String phone;

  @Column(length = 12, nullable = false, unique = true)
  private String cpf;

  @CreationTimestamp 
  private LocalDate createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
  
}
