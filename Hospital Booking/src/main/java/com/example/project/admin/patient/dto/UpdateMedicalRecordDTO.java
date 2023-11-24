package com.example.project.admin.patient.dto;

import com.example.project.admin.patient.Patient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMedicalRecordDTO {
    @NotNull
    private Long id;
    private Patient patient;
    private String diagnosis;
    private String treatment;
    private String prescription;
    private String documentPath;
    private LocalDate recordDate;
}
