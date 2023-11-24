package com.example.project.base.auth;

import com.example.project.base.constant.TokenType;
import com.example.project.util.email.EmailService;
import com.example.project.base.exception.ExpiredException;
import com.example.project.base.exception.NotFoundException;
import com.example.project.util.token.JwtUtil;
import com.example.project.util.token.Token;
import com.example.project.util.token.TokenRepository;
import com.example.project.admin.user.User;
import com.example.project.admin.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;
    private final EmailService emailService;

    @Override
    public void sendResetPasswordEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        String resetToken = jwtUtil.generateConfirmToken(user);
        jwtUtil.saveUserToken(user, resetToken, TokenType.VERIFY);
        String subject = "Reset password";
        String content = "http://localhost:8080/reset-password?token=" + resetToken;
        emailService.send(subject, content, user.getEmail());
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        Token resetToken = tokenRepository.findByValue(token)
                .orElseThrow(() -> new ExpiredException("Email không hợp lệ"));
        User user = resetToken.getUser();
        if (!jwtUtil.isTokenValid(token)) {
            throw new ExpiredException("Email đã hết hạn");
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        jwtUtil.revokeAllUserTokens(user);
    }

    @Override
    public boolean checkResetPasswordToken(String token) {
        try {
            return jwtUtil.isTokenValid(token);
        } catch (Exception e) {
            return false;
        }
    }
}
