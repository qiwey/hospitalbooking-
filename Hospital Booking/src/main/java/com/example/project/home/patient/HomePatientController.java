package com.example.project.home.patient;

import com.example.project.admin.appointment.Appointment;
import com.example.project.admin.appointment.AppointmentService;
import com.example.project.admin.appointment.dto.AppointmentDTO;
import com.example.project.admin.homepage.HomePageInfo;
import com.example.project.admin.homepage.HomePageService;
import com.example.project.admin.patient.MedicalRecord;
import com.example.project.admin.patient.Patient;
import com.example.project.admin.patient.PatientService;
import com.example.project.admin.patient.dto.PatientDTO;
import com.example.project.admin.patient.dto.UpdateMedicalRecordDTO;
import com.example.project.admin.staff.StaffService;
import com.example.project.util.UtilHelper;
import com.example.project.util.email.EmailService;
import com.example.project.util.excel.ExcelService;
import com.example.project.util.search.SearchService;
import com.example.project.util.storage.StorageService;
import com.example.project.util.token.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomePatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final JwtUtil jwtUtil;
    private final HomePageService homePageService;
    private final EmailService emailService;

    @ModelAttribute("home")
    public HomePageInfo homePageInfo() {
        return homePageService.getHomePage();
    }

    @GetMapping("/ho-so-benh-nhan")
    public String getPatientList(
            @RequestParam(required = false) String logout,
            HttpSession session
    ) {
        if (logout != null) session.removeAttribute("email");
        else if (session.getAttribute("email") != null) return "redirect:/danh-sach-benh-nhan";
        return "homepage/patient";
    }

    @PostMapping("/benh-nhan")
    public String getPatientList(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String otp,
            Model model,
            HttpSession session) {
        if (otp == null) {
            if (email == null) return "redirect:/ho-so-benh-nhan?error";
            List<Patient> patients = patientService.getPatientByEmail(email);
            if (patients.isEmpty()) return "redirect:/ho-so-benh-nhan?error";
            String newOtp = UtilHelper.getRandomNumber(6);
            patientService.createOtp(patients, newOtp);
            emailService.send("Otp", "Mã xác thực của bạn là " + newOtp, email);
            return "redirect:/ho-so-benh-nhan?email=" + email;
        }
        List<Patient> patients = patientService.getPatientByEmail(email);
        if (patients.isEmpty()) return "redirect:/ho-so-benh-nhan?error";
        boolean checkOtp = patients.stream().allMatch(patient -> {
            if (patient.getVerificationCode() == null) return false;
            return patient.getVerificationCode().equals(otp);
        });
        if (!checkOtp) {
            return "redirect:/ho-so-benh-nhan?error&email=" + email;
        }
        patientService.deactivateOtp(patients);
        session.setAttribute("email", email);
        return "redirect:/danh-sach-benh-nhan";
    }

    @GetMapping("/danh-sach-benh-nhan")
    public String showPatientList(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        List<Patient> patients = patientService.getPatientByEmail(email);
        model.addAttribute("patients", patients);
        return "homepage/patient-list";
    }

    @GetMapping("/benh-nhan/{id}")
    public String showPatientDetails(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("email");
        if (email == null || email.isBlank()) return "redirect:/ho-so-benh-nhan";
        Patient patientOptional = patientService.getPatientById(id);
        if (patientOptional == null || !patientOptional.getEmail().equals(email)) return "redirect:/ho-so-benh-nhan";
        List<MedicalRecord> medicalRecordList = patientService.getMedicalRecordById(patientOptional);
        List<AppointmentDTO> appointments = patientService.getAppointmentByPatient(patientOptional);
        model.addAttribute("appointments", appointments);
        Map<MedicalRecord, List<String>> medicalRecordFilesMap = new HashMap<>();
        List<UpdateMedicalRecordDTO> updateMedicalRecordDTOS = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecordList) {
            List<String> files = patientService.getDocumentPaths(medicalRecord);
            medicalRecordFilesMap.put(medicalRecord, files);
            UpdateMedicalRecordDTO updateMedicalRecordDTO = patientService.getUpdateMedicalRecordID(medicalRecord.getId());
            updateMedicalRecordDTOS.add(updateMedicalRecordDTO);
        }
        for (Map.Entry<MedicalRecord, List<String>> entry : medicalRecordFilesMap.entrySet()) {
            MedicalRecord medicalRecord = entry.getKey();
            Long medicalId = medicalRecord.getId();
            System.out.println("Medical ID: " + medicalId);

            List<String> files = entry.getValue();
            for (String file : files) {
                System.out.println("File: " + file);
            }
        }
        if (patientOptional != null) {
            model.addAttribute("patient", patientOptional);
            model.addAttribute("medical_record", medicalRecordList);
            model.addAttribute("medicalRecordFilesMap", medicalRecordFilesMap);
            model.addAttribute("updateMedicalRecordDTOs", updateMedicalRecordDTOS);
            return "homepage/patient-details";
        } else {
            return "redirect:/ho-so-benh-nhan/" + id;
        }
    }
}
