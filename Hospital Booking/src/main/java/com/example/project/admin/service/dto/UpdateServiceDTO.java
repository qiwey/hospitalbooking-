package com.example.project.admin.service.dto;

import com.example.project.base.constant.ServiceQuality;
import com.example.project.base.constant.ServiceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateServiceDTO {
    private Long id;
    @NotBlank(message = "Tên dịch vụ không được để trống")
    private String name;
    @NotBlank(message = "Mô tả dịch vụ nhanh không được để trống")
    private String shortDescription;
    @NotBlank(message = "Mô tả đầy đủ dịch vụ không được để trống")
    private String fullDescription;
    @NotNull(message = "Loại dịch vụ không được để trống")
    private ServiceType type;
    @NotNull(message = "Chất lượng dịch vụ không được để trống")
    private ServiceQuality quality;
    @NotNull(message = "Giá dịch vụ không được để trống")
//    @Min(message = "Giá dịch vụ phải lớn hơn 0")
    private String price;
    @NotNull
    private Long doctorId;
}
