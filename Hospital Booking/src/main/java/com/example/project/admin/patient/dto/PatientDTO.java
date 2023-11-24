package com.example.project.admin.patient.dto;

import com.example.project.base.constant.Gender;
import com.example.project.base.constant.InsuranceProvider;
import com.example.project.util.search.Searchable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO implements Searchable {
    private Long id;
    private String fullName;
    private Gender gender;
    private String contactNumber;
    private String email;
    private String address;
    private String nationalIdCard;
    private InsuranceProvider insuranceProvider;
    private LocalDateTime lastModifiedAt;

    public String getLastModifiedAtFormatted() {
        // Định dạng trường lastModifiedAt thành chuỗi với một chữ số thập phân cho giây
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.S");
        return lastModifiedAt.format(formatter);
    }

    @Override
    public String getSearchableString() {
        return fullName + " " + gender + " " + address + " " + email + " " + nationalIdCard + " " + contactNumber;
    }

}
