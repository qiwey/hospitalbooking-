package com.example.project.admin.appointment;

import com.example.project.admin.appointment.dto.AppointmentDTO;
import com.example.project.base.constant.BookingStatus;
import com.example.project.util.email.EmailService;
import com.example.project.admin.user.User;
import com.example.project.util.search.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final EmailService emailService;
    private final SearchService searchService;

    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        return authentication != null ? (User) authentication.getPrincipal() : null;
    }

    @GetMapping("/appointments")
    public String findAllAppointments(
            Model model,
            @RequestParam(required = false, name = "search") String search,
            @RequestParam(required = false, name = "start") LocalDate startDate,
            @RequestParam(required = false, name = "end") LocalDate endDate,
            @PageableDefault(
                    sort = {"time", "createdAt"},
                    direction = Sort.Direction.ASC
            ) Pageable pageable,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Page<AppointmentDTO> page;
        if (startDate == null) {
            startDate = LocalDate.now();
            endDate = LocalDate.now().plusDays(3);
        }
        if (search != null && !search.isBlank()) {
            List<AppointmentDTO> appointments = appointmentService.getAllAppointmentsWithDate(user, startDate, endDate);
            page = searchService.search(search, appointments, pageable);
        } else {
            page = appointmentService.getAllAppointmentsWithDate(user, startDate, endDate, pageable);
        }
        model.addAttribute("page", page);
        return "dashboard/appointment/appointments";
    }

    @GetMapping("/appointment/change-status")
    public String changeStatus(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "status") BookingStatus status,
            HttpServletRequest request
    ) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        appointmentService.updateStatus(appointment, status);
        appointmentService.sendEmailAppointment(appointment, status);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
