package com.example.project.admin.appointment;

import com.example.project.admin.appointment.dto.AppointmentDTO;
import com.example.project.admin.patient.Patient;
import com.example.project.base.constant.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select count(a) from Appointment a where month(a.time) = :month and year(a.time) = :year")
    long countByTime(int month, int year);

    @Query("""
            SELECT a FROM Appointment a
            WHERE DATE(a.time) = :date
            AND a.service.doctor.id = :doctorId
            AND (a.status != 'REJECTED' AND a.status != 'DONE')
            """)
    List<Appointment> findAllByTimeDateAndDoctor(LocalDate date, Long doctorId);

    @Query("""
            select a
            from Appointment a
            where a.time between :start and :end
            and (a.status != 'REJECTED')
            and a.service.doctor.id = :doctorId
            """)
    List<Appointment> findAllAppointment(LocalDateTime start, LocalDateTime end, Long doctorId);

    @Query(value = """
                SELECT new com.example.project.admin.appointment.dto.AppointmentDTO
                (
                    a.id,
                    a.patient.id,
                    a.patient.fullName,
                    YEAR(CURRENT_DATE) - YEAR(a.patient.dateOfBirth),
                    a.patient.contactNumber,
                    a.service.name,
                    CONCAT(a.service.doctor.lastName, ' ', a.service.doctor.firstName),
                    a.service.doctor.department.name,
                    a.reason,
                    a.time,
                    a.status,
                    a.payment.paymentStatus,
                    a.payment.id,
                    a.createdAt
                )
                FROM Appointment a
                WHERE a.patient.id = :patientId
            """
    )
    List<AppointmentDTO> findAllByPatient(Long patientId);

    @Query(value = """
                SELECT new com.example.project.admin.appointment.dto.AppointmentDTO
                (
                    a.id,
                    a.patient.id,
                    a.patient.fullName,
                    YEAR(CURRENT_DATE) - YEAR(a.patient.dateOfBirth),
                    a.patient.contactNumber,
                    a.service.name,
                    CONCAT(a.service.doctor.lastName, ' ', a.service.doctor.firstName),
                    a.service.doctor.department.name,
                    a.reason,
                    a.time,
                    a.status,
                    a.payment.paymentStatus,
                    a.payment.id,
                    a.createdAt
                )
                FROM Appointment a
                WHERE a.time BETWEEN :startDate AND :endDate
            """
    )
    Page<AppointmentDTO> findAllAppointmentsWithDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query(value = """
                SELECT new com.example.project.admin.appointment.dto.AppointmentDTO
                (
                    a.id,
                    a.patient.id,
                    a.patient.fullName,
                    YEAR(CURRENT_DATE) - YEAR(a.patient.dateOfBirth),
                    a.patient.contactNumber,
                    a.service.name,
                    CONCAT(a.service.doctor.lastName, ' ', a.service.doctor.firstName),
                    a.service.doctor.department.name,
                    a.reason,
                    a.time,
                    a.status,
                    a.payment.paymentStatus,
                    a.payment.id,
                    a.createdAt
                )
                FROM Appointment a
                WHERE a.service.doctor.id = :id
                AND a.time BETWEEN :start AND :end
            """
    )
    Page<AppointmentDTO> findAllAppointmentsWithDateAndDoctor(Long id, LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Appointment> findAppointmentsByStatus(BookingStatus appointmentStatus);

    @Query(value = """
                SELECT new com.example.project.admin.appointment.dto.AppointmentDTO
                (
                    a.id,
                    a.patient.id,
                    a.patient.fullName,
                    YEAR(CURRENT_DATE) - YEAR(a.patient.dateOfBirth),
                    a.patient.contactNumber,
                    a.service.name,
                    CONCAT(a.service.doctor.lastName, ' ', a.service.doctor.firstName),
                    a.service.doctor.department.name,
                    a.reason,
                    a.time,
                    a.status,
                    a.payment.paymentStatus,
                    a.payment.id,
                    a.createdAt
                )
                FROM Appointment a
                WHERE a.service.doctor.department.id = :departId
                AND a.time BETWEEN :start AND :end
            """
    )
    Page<AppointmentDTO> findAllAppointmentsWithDateAndDepart(Long departId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query(value = """
                SELECT new com.example.project.admin.appointment.dto.AppointmentDTO
                (
                    a.id,
                    a.patient.id,
                    a.patient.fullName,
                    YEAR(CURRENT_DATE) - YEAR(a.patient.dateOfBirth),
                    a.patient.contactNumber,
                    a.service.name,
                    CONCAT(a.service.doctor.lastName, ' ', a.service.doctor.firstName),
                    a.service.doctor.department.name,
                    a.reason,
                    a.time,
                    a.status,
                    a.payment.paymentStatus,
                    a.payment.id,
                    a.createdAt
                )
                FROM Appointment a
            """
    )
    List<AppointmentDTO> findAllAppointmentDto();

    @Query(value = """
            SELECT
                COALESCE(COUNT(a.appointment_id), 0) AS total_appointments
            FROM
                (SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION
                 SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) m
                    LEFT JOIN
                appointment a ON MONTH(a.appointment_time) = m.month AND YEAR(a.appointment_time) = 2023
            GROUP BY
                m.month
            ORDER BY
                m.month;
                    """,
            nativeQuery = true)
    List<Integer> findAppointmentInYear(int year);
}
