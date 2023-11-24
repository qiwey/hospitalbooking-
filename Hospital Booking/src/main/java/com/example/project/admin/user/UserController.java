package com.example.project.admin.user;

import com.example.project.admin.schedule.DoctorScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class UserController {

    private final UserService userService;
    private final DoctorScheduleService doctorScheduleService;

    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        return authentication != null ? (User) authentication.getPrincipal() : null;
    }

    @GetMapping("/profile")
    public String profile(
            Authentication authentication,
            @RequestParam(name = "week", required = false) LocalDate firstDayOfWeek,
            Model model
    ) {
        User user = (User) authentication.getPrincipal();
        if (firstDayOfWeek == null) {
            firstDayOfWeek = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        }
        List<String> times = doctorScheduleService.getSchedule(user, firstDayOfWeek);
        model.addAttribute("times", times);
        log.info("User: {}", user.getDepartment());
        return "dashboard/user/profile";
    }

    @GetMapping("/profile/change-password")
    public String changePassword(Model model) {
        model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
        return "dashboard/user/change-password";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(ChangePasswordDTO changePasswordDTO, Authentication authentication, HttpSession session) {
        User user = (User) authentication.getPrincipal();
        try {
            userService.changePassword(changePasswordDTO, user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/dashboard/profile/change-password?error";
        }
        session.invalidate();
        return "redirect:/dashboard/profile";
    }
}
