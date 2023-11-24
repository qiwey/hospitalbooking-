package com.example.project.admin.department.dto;

import com.example.project.util.search.Searchable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO implements Searchable {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Boolean activeStatus;
    private LocalDateTime lastModifiedAt;

    @Override
    public String getSearchableString() {
        return name + " " + description;
    }
}

