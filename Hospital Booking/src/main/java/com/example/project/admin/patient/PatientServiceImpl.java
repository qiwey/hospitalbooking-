package com.example.project.admin.patient;

import com.example.project.admin.appointment.Appointment;
import com.example.project.admin.appointment.AppointmentRepository;
import com.example.project.admin.appointment.AppointmentService;
import com.example.project.admin.appointment.dto.AppointmentDTO;
import com.example.project.admin.staff.StaffService;
import com.example.project.admin.user.Role;
import com.example.project.admin.user.User;
import com.example.project.admin.user.UserRepository;
import com.example.project.admin.user.UserService;
import com.example.project.base.exception.NotFoundException;
import com.example.project.util.UtilHelper;
import com.example.project.util.storage.StorageService;
import com.example.project.admin.patient.dto.NewMedicalRecordDTO;
import com.example.project.admin.patient.dto.NewPatientDTO;
import com.example.project.admin.patient.dto.PatientDTO;
import com.example.project.admin.patient.dto.UpdateMedicalRecordDTO;
import com.example.project.admin.patient.dto.UpdatePatientDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    //    private final DoctorRepository doctorRepository;
    private final StorageService storageService;
    private final ModelMapper mapper;
    private final StaffService staffService;
//    private  final  MedicalRecordFilesRepository medicalRecordFilesRepository;

    @Override
    public Patient createPatient(NewPatientDTO newPatientDTO) {
        Patient patient = mapper.map(newPatientDTO, Patient.class);

        // Save the patient in the repository
        return patientRepository.save(patient);
    }

    @Override
    public Page<PatientDTO> getPatientsModifiedBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        if (startDate != null && endDate != null) {
            // Sử dụng khoảng thời gian để tìm kiếm
            Page<Patient> patients = patientRepository.findPatientsByLastModifiedBetween(startDate, endDate, pageable);
            Page<PatientDTO> patientDTOPage = patients.map(
                    patient -> mapper.map(patient, PatientDTO.class)
            );
            return patientDTOPage;
        } else {
            // Nếu không có tìm kiếm theo ngày tháng, trả về tất cả bệnh nhân
            return getAllPatients(null, pageable);
        }
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> patientList = patientRepository.findAll();
        List<PatientDTO> patientDTOList = patientList.stream()
                .map(patient -> mapper.map(patient, PatientDTO.class))
                .collect(Collectors.toList());
        return patientDTOList;
    }

    @Override
    public Page<PatientDTO> getAllPatients(String search, Pageable pageable) {
        Page<Patient> patientsPage;

        if (search != null && !search.isEmpty()) {
            patientsPage = patientRepository.findPatientsBySearchText(search, pageable);
        } else {
            patientsPage = patientRepository.findAll(pageable);
        }

        Page<PatientDTO> patientDTOPage = patientsPage.map(
                patient -> mapper.map(patient, PatientDTO.class)
        );

        return patientDTOPage;
    }


    @Override
    public Patient updatePatient(Patient patient, UpdatePatientDTO updatePatientDTO) {
        // Map the properties from the DTO to the existing patient entity
        mapper.map(updatePatientDTO, patient);

        // Save the updated patient in the repository
        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Patient patient) {
        patientRepository.delete(patient);
    }

    @Override
    public List<MedicalRecord> getMedicalRecordById(Patient patient) {
        List<MedicalRecord> medicalRecordList = medicalRecordRepository.findByPatient(patient);
        return medicalRecordList;
    }


    public List<String> getDocumentPaths(MedicalRecord medicalRecord) {
        String documentPath = medicalRecord.getDocumentPath();
        if (documentPath != null && !documentPath.isEmpty()) {
            String[] paths = documentPath.split("[;\\s]+");
            return Arrays.asList(paths);
        } else {
            return Collections.emptyList();
        }

    }

    @Override
    public MedicalRecord findMedicalId(Long id) {
        return medicalRecordRepository.findMedicalRecordById(id);
    }

    @Override
    public MedicalRecord createMedicalRecord(NewMedicalRecordDTO newMedicalRecordDTO) {
        Patient patient = getPatientById(newMedicalRecordDTO.getPatientId());
        User doctor = userRepository.findById(newMedicalRecordDTO.getDoctorId()).orElseThrow(() -> new NotFoundException("Doctor not found"));
        MedicalRecord medicalRecord = mapper.map(newMedicalRecordDTO, MedicalRecord.class);
        if (newMedicalRecordDTO.getDocumentPath() != null && !newMedicalRecordDTO.getDocumentPath().isEmpty()) {
            String documentPath = storageService.store(newMedicalRecordDTO.getDocumentPath());
            medicalRecord.setDocumentPath(documentPath);
        } else {
            medicalRecord.setDocumentPath(null);
        }
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        // Save the patient in the repository
        return medicalRecordRepository.save(medicalRecord);
    }


    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord, UpdateMedicalRecordDTO updateMedicalRecordDTO) {
        mapper.map(updateMedicalRecordDTO, medicalRecord);
        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public List<Patient> getPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email);
    }

    @Override
    public List<AppointmentDTO> getAppointmentByPatient(Patient patient) {
        return appointmentRepository.findAllByPatient(patient.getId());
    }

    @Override
    public void createOtp(List<Patient> patients, String otp) {
        for (Patient patient : patients) {
            patient.setVerificationCode(otp);
        }
        patientRepository.saveAll(patients);
    }

    @Override
    public void deactivateOtp(List<Patient> patients) {
        createOtp(patients, null);
    }

    @Override
    public Page<PatientDTO> getAllPatients(Pageable pageable, Long doctorId) {
        return patientRepository.findPatientByDoctor(pageable, doctorId).map((element) -> mapper.map(element, PatientDTO.class));
    }

    @Override
    public Page<PatientDTO> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable).map((element) -> mapper.map(element, PatientDTO.class));

    }

    @Override
    public UpdatePatientDTO getUpdatePatientById(Long id) {
        Patient patient = getPatientById(id);
        return mapper.map(patient, UpdatePatientDTO.class);
    }

    @Override
    public UpdateMedicalRecordDTO getUpdateMedicalRecordID(Long id) {
        MedicalRecord medicalRecord = findMedicalId(id);
        return mapper.map(medicalRecord, UpdateMedicalRecordDTO.class);
    }

    @Override
    @Transactional
    public MedicalRecord creMedicalRecord(NewMedicalRecordDTO newMedicalRequest) {
        // Lấy thông tin bệnh nhân
        Patient patient = getPatientById(newMedicalRequest.getPatientId());

        // Lấy thông tin bác sĩ
        User doctor = staffService.getStaffById(newMedicalRequest.getDoctorId());

        // Chuyển đổi DTO thành đối tượng MedicalRecord
        MedicalRecord medicalRecord = mapper.map(newMedicalRequest, MedicalRecord.class);

        // Kiểm tra và lưu đường dẫn tài liệu nếu tồn tại
        if (newMedicalRequest.getDocumentPath() != null && !newMedicalRequest.getDocumentPath().isEmpty()) {
            medicalRecord.setDocumentPath(storageService.store(newMedicalRequest.getDocumentPath()));
        }

        // Liên kết với bệnh nhân và bác sĩ
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);

        // Lưu đối tượng MedicalRecord
        return medicalRecordRepository.save(medicalRecord);
    }

}
