package com.example.project.admin.service;

import com.example.project.base.constant.ServiceQuality;
import com.example.project.base.constant.ServiceType;
import com.example.project.admin.user.User;
import com.example.project.util.search.Searchable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_service")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DoctorService implements Searchable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long id;

    @Column(name = "service_name", length = 255)
    private String name;

    @Column(name = "service_short_desc", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "service_full_desc", columnDefinition = "LONGTEXT")
    private String fullDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_quality")
    private ServiceQuality quality;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @Column(name = "price")
    private Double price;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        lastModifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedAt = LocalDateTime.now();
    }

    @Override
    public String getSearchableString() {
        return name + " " + type.getValue() + " " + quality.getValue() + " " + doctor.getFullName() + " " + doctor.getDepartment().getName() + " " + shortDescription;
    }
}

