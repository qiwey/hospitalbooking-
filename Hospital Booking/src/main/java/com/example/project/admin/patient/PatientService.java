package com.example.project.admin.patient;

import com.example.project.admin.appointment.Appointment;
import com.example.project.admin.appointment.dto.AppointmentDTO;
import com.example.project.admin.patient.dto.*;
import com.example.project.admin.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientService {
    Patient createPatient(NewPatientDTO newPatientRequest);

    MedicalRecord creMedicalRecord(NewMedicalRecordDTO newMedicalRequest);

    Patient getPatientById(Long id);

    Page<PatientDTO> getAllPatients(String search, Pageable pageable);

    Page<PatientDTO> getPatientsModifiedBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Patient updatePatient(Patient patient, UpdatePatientDTO updatePatientDTO);

    void deletePatient(Patient patient);

    UpdatePatientDTO getUpdatePatientById(Long id);

    UpdateMedicalRecordDTO getUpdateMedicalRecordID(Long id);

    MedicalRecord findMedicalId(Long id);

    List<PatientDTO> getAllPatients();

    List<MedicalRecord> getMedicalRecordById(Patient patient);

    List<String> getDocumentPaths(MedicalRecord medicalRecord);

    MedicalRecord createMedicalRecord(NewMedicalRecordDTO newMedicalRecordDTO);

    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord, UpdateMedicalRecordDTO updateMedicalRecordDTO);

    List<Patient> getPatientByEmail(String email);

    List<AppointmentDTO> getAppointmentByPatient(Patient patient);

    void createOtp(List<Patient> patient, String otp);

    void deactivateOtp(List<Patient> patients);

    Page<PatientDTO> getAllPatients(Pageable pageable, Long id);

    Page<PatientDTO> getAllPatients(Pageable pageable);
}
