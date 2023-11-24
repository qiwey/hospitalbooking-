package com.example.project.admin.patient;

import com.example.project.admin.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "user_id")
    private User doctor;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "treatment")
    @Lob
    private String treatment;

    @Column(name = "prescription")
    private String prescription;

    @Column(name = "document")
    private String documentPath;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_modified_at", nullable = false)
    private LocalDateTime lastModified = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        lastModified = LocalDateTime.now();
    }
}
