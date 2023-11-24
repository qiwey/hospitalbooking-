package com.example.project.home.service;

import com.example.project.base.constant.ServiceQuality;
import com.example.project.base.constant.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ServiceHomeDTO {
    private Long id;
    private String name;
    private String description;
    private ServiceType type;
    private ServiceQuality quality;
    private String doctorName;
    private String departmentName;
    private Double price;
    private LocalDateTime createdAt;
}
