package com.example.project.admin.patient.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NewMedicalRecordDTO {
    @NotNull
    private Long patientId;
    @NotNull
    private Long doctorId;
    @NotBlank
    private String diagnosis;
    @NotBlank
    private String treatment;
    @NotBlank
    private String prescription;
    private MultipartFile documentPath;
    private LocalDate recordDate;
}
