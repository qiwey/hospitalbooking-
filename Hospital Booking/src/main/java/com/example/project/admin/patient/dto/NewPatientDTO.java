package com.example.project.admin.patient.dto;

import com.example.project.base.constant.Gender;
import com.example.project.base.constant.InsuranceProvider;
import com.example.project.base.validation.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NewPatientDTO {
    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;
    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate dateOfBirth;
    @NotNull(message = "Giới tính không được để trống")
    private Gender gender;
    @NotNull(message = "CCCD không được bỏ trống")
    private String nationalIdCard;
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;
    @NotBlank(message = "Số điện thoại không được để trống")
    private String contactNumber;
    @NotNull(message = "Nơi cấp BHYT không được để trống")
    private InsuranceProvider insuranceProvider;
    private String address;
}
