package com.example.project.admin.homepage;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateHomePageDTO {
    private MultipartFile logo;
    private String hospitalName;
    private String phoneNumber;
    private String email;
    private String address;
    private String aboutUsTitle;
    private String aboutUsDesc;
    private String departmentTitle;
    private String departmentDesc;
    private String doctorTitle;
    private String doctorDesc;
    private String serviceTitle;
    private String serviceDesc;
}
