package com.example.project.util.email;

import com.example.project.base.constant.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {
    @NotBlank(message = "Tên không được để trống")
    private String fullName;
    @NotBlank(message = "Email không được để trống")
    private String email;
    @NotBlank(message = "Tiêu đề đăng nhập không được để trống")
    private String subject;
    @NotBlank(message = "Nội dung không được để trống")
    private String content;
}
