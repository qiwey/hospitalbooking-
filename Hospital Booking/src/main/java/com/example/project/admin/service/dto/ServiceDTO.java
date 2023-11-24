package com.example.project.admin.service.dto;

import com.example.project.base.constant.ServiceQuality;
import com.example.project.base.constant.ServiceType;
import com.example.project.util.search.Searchable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ServiceDTO implements Searchable {
    private Long id;
    private String name;
    private String description;
    private ServiceType type;
    private ServiceQuality quality;
    private String doctorName;
    private String departmentName;
    private Double price;
    private LocalDateTime lastModifiedAt;

    @Override
    public String getSearchableString() {
        return name + " " + description + " " + departmentName + " " + doctorName + " " + type.getValue() + " " + quality.getValue();
    }
}
