package com.example.project.admin.feedback;

import com.example.project.admin.appointment.Appointment;
import com.example.project.admin.appointment.AppointmentService;
import com.example.project.admin.homepage.HomePageInfo;
import com.example.project.admin.homepage.HomePageService;
import com.example.project.admin.service.DoctorService;
import com.example.project.admin.service.ServiceService;
import com.example.project.admin.service.dto.ServiceDTO;
import com.example.project.admin.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final ServiceService serviceService;
    private final AppointmentService appointmentService;

    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        return authentication != null ? (User) authentication.getPrincipal() : null;
    }

    @GetMapping("/feedbacks")
    public String feedbacks(
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable,
            @RequestParam(required = false) Long serviceId,
            Model model,
            Authentication authentication
    ) {
        Page<Feedback> feedbacks;
        User user = (User) authentication.getPrincipal();
        if (serviceId != null) {
            feedbacks = feedbackService.getFeedbacksService(pageable, serviceId);
        } else if ("ASSIS".equals(user.getRole().getId())) {
            feedbacks = feedbackService.getFeedbacksService(pageable, user.getDepartment());
        } else {
            feedbacks = feedbackService.getFeedbacks(pageable);
        }
        List<ServiceDTO> services = serviceService.getAllServices();
        if ("ASSIS".equals(user.getRole().getId())) {
            services = services.stream().filter(s -> s.getDepartmentName().equals(user.getDepartment().getName())).toList();
        }
        model.addAttribute("services", services);
        model.addAttribute("page", feedbacks);
        return "dashboard/feedback/feedbacks";
    }
}
