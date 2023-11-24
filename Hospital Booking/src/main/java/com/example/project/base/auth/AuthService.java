package com.example.project.base.auth;

public interface AuthService {
    void sendResetPasswordEmail(String email);

    boolean checkResetPasswordToken(String token);

    void resetPassword(String token, String newPassword);
}
