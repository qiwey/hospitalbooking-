package com.example.project.admin.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCategoryDTO {
    @NotNull
    private Long Id;
    @NotBlank(message = "Tên không được để trống")
    private String name;
    @NotBlank(message = "Mô tả không được để trống")
    private String description;
    @NotNull(message = "Trạng thái không được để trống")
    private boolean activeStatus;

}
