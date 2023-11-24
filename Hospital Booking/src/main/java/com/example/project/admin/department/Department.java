package com.example.project.admin.department;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;

    @Column(name = "department_code", length = 5)
    private String code;

    @Column(name = "department_name", length = 50)
    private String name;

    @Column(name = "department_short_desc", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "department_full_desc", columnDefinition = "LONGTEXT")
    private String fullDescription;

    @Column(name = "is_active")
    private Boolean activeStatus = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedAt = LocalDateTime.now();
    }
}
