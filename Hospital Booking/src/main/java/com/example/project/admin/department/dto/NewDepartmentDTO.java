package com.example.project.admin.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data

public class NewDepartmentDTO {
    @NotBlank(message = "Mã phòng ban không được để trống")
    @Length(max = 5, message = "ID không được quá 5 ký tự")
    private String code;

    @NotBlank(message = "Tên phòng ban không được để trống")
    private String name;

    @NotBlank(message = "Mô tả ngắn không được để trống")
    private String shortDescription;

    @NotBlank(message = "Mô tả đầy đủ không được để trống")
    private String fullDescription;

    @NotNull(message = "Trạng thái không được để trống")
    private Boolean activeStatus;
}
