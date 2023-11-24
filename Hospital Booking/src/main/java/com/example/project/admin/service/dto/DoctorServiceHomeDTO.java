package com.example.project.admin.service.dto;

import com.example.project.base.constant.ServiceQuality;
import com.example.project.base.constant.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DoctorServiceHomeDTO {
    private Long id;
    private String name;
    private String description;
    private String doctorName;
    private ServiceType type;
    private ServiceQuality quality;
    private String departmentName;
    private Double price;
    private LocalDateTime lastModifiedAt;
}
