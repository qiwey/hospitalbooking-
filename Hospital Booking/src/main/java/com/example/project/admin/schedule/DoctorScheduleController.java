package com.example.project.admin.schedule;

import com.example.project.admin.user.User;
import com.example.project.admin.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class DoctorScheduleController {

//    private final DoctorScheduleService doctorScheduleService;
//    private final UserService userService;
//
//    @ModelAttribute("currentUser")
//    public User currentUser(Authentication authentication) {
//        return authentication != null ? (User) authentication.getPrincipal() : null;
//    }
//
//    @GetMapping("/schedule/{id}")
//    public String schedule(
//            @PathVariable Long id,
//            @RequestParam(required = false) LocalDate week,
//            Model model) {
//        User user = userService.getUserById(id);
//        int month = LocalDate.now().getMonthValue();
//        model.addAttribute("userId", id);
//        model.addAttribute("schedules", doctorScheduleService.getAllAvailableScheduleOfDoctor(user, month));
//        return "dashboard/schedule/schedules";
//    }
//
//    @PostMapping("/schedule/{id}")
//    public String schedule(
//            @PathVariable Long id,
//            @RequestParam(name = "week") LocalDate firstDayOfWeek,
//            @RequestParam List<String> time) {
//        if (time == null) {
//            time = new ArrayList<>();
//        }
//        doctorScheduleService.saveSchedule(id, time, firstDayOfWeek);
//        return "redirect:/dashboard/schedule/" + id;
//    }
}
