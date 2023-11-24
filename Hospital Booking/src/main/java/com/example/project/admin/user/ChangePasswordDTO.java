package com.example.project.admin.user;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
