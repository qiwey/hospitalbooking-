package com.example.project.home.feedback;

import com.example.project.admin.appointment.Appointment;
import com.example.project.admin.appointment.AppointmentService;
import com.example.project.admin.feedback.FeedbackService;
import com.example.project.admin.homepage.HomePageInfo;
import com.example.project.admin.homepage.HomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class FeedbackHomeController {
    private final HomePageService homePageService;
    private final FeedbackService feedbackService;
    private final AppointmentService appointmentService;


    @ModelAttribute("home")
    public HomePageInfo homePageInfo() {
        return homePageService.getHomePage();
    }

    @GetMapping("/danh-gia")
    public String feedback() {
        return "homepage/feedback";
    }

    @PostMapping("/danh-gia/{appointmentId}")
    public String feedback(@PathVariable Long appointmentId, @RequestParam String comment) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        feedbackService.createFeedback(appointment, comment);
        return "redirect:/danh-gia?success";
    }
}
