package com.example.project.home.appointment;

import com.example.project.base.constant.Gender;
import com.example.project.base.constant.InsuranceProvider;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NewAppointmentDTO {
    private String patientName;
    private Gender patientGender;
    private LocalDate patientDateOfBirth;
    private String patientContactNumber;
    private String patientEmail;
    private String patientNationalIdCard;
    private String patientAddress;
    private String reason;
    private LocalDateTime time;
    private InsuranceProvider insuranceProvider;
}
