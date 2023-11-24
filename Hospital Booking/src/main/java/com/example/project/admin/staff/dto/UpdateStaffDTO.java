package com.example.project.admin.staff.dto;

import com.example.project.base.constant.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UpdateStaffDTO {
    private Long id;
    @NotBlank(message = "Tên không được để trống")
    private String firstName;
    @NotBlank(message = "Họ không được để trống")
    private String lastName;
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String email;
    @NotBlank(message = "Mật khẩu không được để trống")
    private String contactNumber;
    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate dateOfBirth;
    private String description;
    @NotNull(message = "Giới tính không được để trống")
    private Gender gender;
    @NotNull(message = "Vai trò không được để trống")
    private String roleId;
    @NotNull(message = "Phòng ban không được để trống")
    private Long departmentId;
    private String address;
    @NotNull(message = "Ảnh đại diện không được để trống")
    private MultipartFile picture;
    @NotNull(message = "Trạng thái không được để trống")
    private Boolean lockedStatus = false;
}
