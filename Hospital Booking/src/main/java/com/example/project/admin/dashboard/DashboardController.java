package com.example.project.admin.dashboard;

import com.example.project.admin.appointment.AppointmentRepository;
import com.example.project.admin.patient.PatientRepository;
import com.example.project.admin.payment.PaymentRepository;
import com.example.project.admin.user.User;
import com.example.project.admin.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.spi.StrongTypeConditionalConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class DashboardController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final PaymentRepository paymentRepository;

    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user.setPassword(null);
        return user;
    }

    @RequestMapping
    public String dashboard(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        switch (user.getRole().getId()) {
            case "DOC", "RECEP":
                return "redirect:/dashboard/appointments";
            case "ASSIS":
                return "redirect:/dashboard/staffs";
            case "MEDIA":
                return "redirect:/dashboard/articles";
            case "NURSE":
                return "redirect:/dashboard/patients";
        }
        model.addAttribute("numOfPatients", patientRepository.count());
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        model.addAttribute("numOfAppointmentInMonth", appointmentRepository.countByTime(month, year));
        model.addAttribute("numOfDoctors", userRepository.countByRole_Id("DOC"));
        model.addAttribute("totalOfPaymentsMonth", paymentRepository.findTotalInMonth(month));
        String amountEachMonth = Arrays.toString(paymentRepository.findAmountInYear(year)
                .stream().map(a -> a / 1_000_000).toArray());
        model.addAttribute("amountInYear", amountEachMonth);
        String appointmentEachMonth = Arrays.toString(appointmentRepository.findAppointmentInYear(year).toArray());
        model.addAttribute("appointmentInYear", appointmentEachMonth);
        return "dashboard/index";
    }
}
