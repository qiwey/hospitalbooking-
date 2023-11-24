package com.example.project.admin.patient;

import com.example.project.admin.patient.dto.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p " +
            "WHERE LOWER(p.fullName) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(p.email) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(p.contactNumber) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(p.address) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(p.gender) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<Patient> findPatientsBySearchText(@Param("searchText") String searchText, Pageable pageable);

    @Query("SELECT p FROM Patient p  WHERE p.lastModifiedAt BETWEEN :startDate AND :endDate")
    Page<Patient> findPatientsByLastModifiedBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Optional<Patient> findByNationalIdCard(String patientNationalIdCard);

    List<Patient> findPatientByEmail(String email);

    @Query("SELECT p FROM Patient p " +
            "WHERE p.id IN (SELECT a.patient.id FROM Appointment a " +
            "JOIN a.service s " +
            "JOIN s.doctor d " +
            "WHERE d.id = :doctorId)")
    Page<Patient> findPatientByDoctor(Pageable pageable, Long doctorId);
}
