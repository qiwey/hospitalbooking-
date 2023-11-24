package com.example.project.base.auth;

import com.example.project.admin.user.User;
import com.example.project.base.exception.ExpiredException;
import com.example.project.base.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/auth")
    public String loginWithGoogle(Authentication authentication, HttpServletRequest request) {
        Object user = authentication.getPrincipal();
        if (user instanceof OAuth2User oAuth2User) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(
                        oAuth2User.getAttributes().get("email").toString()
                );
                Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
            } catch (UsernameNotFoundException e) {
                request.getSession().invalidate();
                return "redirect:/login?error";
            }
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "dashboard/auth/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "dashboard/auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, Model model) {
        try {
            authService.sendResetPasswordEmail(email);
        } catch (NotFoundException e) {
            return "redirect:/forgot-password?error";
        }
        return "redirect:/forgot-password?success";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam String token, Model model) {
        if (!authService.checkResetPasswordToken(token)) {
            model.addAttribute("expired", true);
        }
        return "dashboard/auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            authService.resetPassword(token, newPassword);
        } catch (ExpiredException | InvalidBearerTokenException e) {
            return "redirect:/reset-password?expired";
        }
        return "redirect:/login";
    }
}
