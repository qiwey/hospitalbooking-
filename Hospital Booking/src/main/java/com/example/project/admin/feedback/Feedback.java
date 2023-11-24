package com.example.project.admin.feedback;

import com.example.project.admin.appointment.Appointment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", referencedColumnName = "appointment_id")
    private Appointment appointment;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at", columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at", columnDefinition = "DATETIME default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime lastModifiedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        lastModifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedAt = LocalDateTime.now();
    }
}
