package com.example.project.admin.appointment;

import com.example.project.admin.payment.Payment;
import com.example.project.base.constant.BookingStatus;
import com.example.project.admin.patient.Patient;
import com.example.project.admin.service.DoctorService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private DoctorService service;

    @Column(name = "appointment_time")
    private LocalDateTime time;

    @Column(name = "reason")
    private String reason;

    @Column(name = "appointment_status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "appointment", cascade = CascadeType.ALL)
    private Payment payment;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @PrePersist
    public void prePersist() {
        status = BookingStatus.PENDING;
        createdAt = LocalDateTime.now();
        lastModifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedAt = LocalDateTime.now();
    }
}
