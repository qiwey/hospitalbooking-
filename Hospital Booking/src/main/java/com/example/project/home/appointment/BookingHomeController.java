package com.example.project.home.appointment;

import com.example.project.admin.appointment.Appointment;
import com.example.project.admin.appointment.AppointmentService;
import com.example.project.admin.service.dto.DoctorServiceHomeDTO;
import com.example.project.base.constant.InsuranceProvider;
import com.example.project.util.email.EmailService;
import com.example.project.admin.homepage.HomePageInfo;
import com.example.project.admin.homepage.HomePageService;
import com.example.project.admin.schedule.DoctorScheduleService;
import com.example.project.admin.service.DoctorService;
import com.example.project.admin.service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookingHomeController {

    private final ServiceService serviceService;
    private final DoctorScheduleService doctorScheduleService;
    private final AppointmentService appointmentService;
    private final EmailService emailService;
    private final HomePageService homePageService;

    @ModelAttribute("home")
    public HomePageInfo homePageInfo() {
        return homePageService.getHomePage();
    }

    @GetMapping("/dat-lich")
    public String getBookingHome(
            @RequestParam(required = false, name = "serviceId") Long id,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Long doctorId,
            Model model
    ) {
        if (doctorId != null) {
            DoctorService service = serviceService.getTop1ServiceByDoctorId(doctorId);
            if (service != null) {
                return "redirect:/dat-lich?serviceId=" + service.getId();
            }
        }
        if (id != null) {
            DoctorService service = serviceService.getServiceById(id);
            List<LocalDate> availableDate = doctorScheduleService.getAvailableDateOfDoctor(service.getDoctor());
            date = date == null ? (availableDate.isEmpty() ? LocalDate.now() : availableDate.get(0)) : date;
            List<LocalDateTime> schedules = doctorScheduleService.getAllAvailableScheduleOfDoctor(service.getDoctor(), date);
            model.addAttribute("service", service);
            model.addAttribute("schedules", schedules);
            model.addAttribute("availableDate", availableDate);
            model.addAttribute("providers", InsuranceProvider.values());
        }
        List<DoctorServiceHomeDTO> services = serviceService.getAllServiceHome();
        model.addAttribute("services", services);
        model.addAttribute("newAppointmentDTO", new NewAppointmentDTO());
        return "homepage/service-detail";
    }

    @PostMapping("/dat-lich/{id}")
    public String postBookingHome(
            @PathVariable Long id,
            @Valid NewAppointmentDTO newAppointmentDTO,
            BindingResult result
    ) {
//        if (result.hasErrors()) {
//            return "homepage/service-detail";
//        }
        DoctorService service = serviceService.getServiceById(id);
        appointmentService.createAppointment(service, newAppointmentDTO);
        String subject = "Đặt lịch khám bệnh thành công";
        String content = "Bạn đã đặt lịch khám bệnh thành công. Vui lòng đến đúng giờ: "
                + newAppointmentDTO.getTime().format(DateTimeFormatter.ISO_DATE_TIME);
        String email = newAppointmentDTO.getPatientEmail();
        emailService.send(subject, content, email);
        return "redirect:/dat-lich?success";
    }
}
