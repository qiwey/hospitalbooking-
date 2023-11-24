package com.example.project.admin.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDTO {
    @NotBlank(message = "Tên chuyên mục không được để trống!")
    String name;
    @NotBlank(message = "Mô tả không được để trống!")
    String description;
    @NotNull(message = "Trạng thái không được để trống!")
    Boolean activeStatus;
}
