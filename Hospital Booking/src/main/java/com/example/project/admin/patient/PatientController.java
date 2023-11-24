package com.example.project.admin.patient;

import com.example.project.admin.appointment.Appointment;
import com.example.project.admin.appointment.AppointmentService;
import com.example.project.admin.appointment.dto.AppointmentDTO;
import com.example.project.admin.payment.Payment;
import com.example.project.admin.payment.PaymentService;
import com.example.project.base.constant.InsuranceProvider;
import com.example.project.admin.patient.dto.*;
import com.example.project.admin.staff.StaffService;
import com.example.project.admin.user.User;
import com.example.project.base.exception.NotFoundException;
import com.example.project.util.email.EmailService;
import com.example.project.util.excel.ExcelService;
import com.example.project.base.exception.FileException;
import com.example.project.base.exception.StorageException;
import com.example.project.util.search.SearchService;
import com.example.project.util.storage.StorageService;
import com.example.project.base.exception.DuplicateException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class PatientController {


    private final PatientService patientService;
    private final ExcelService excelService;
    private final SearchService searchService;
    private final StorageService storageService;
    private final StaffService staffService;
    private final EmailService emailService;
    private final PaymentService paymentService;
    private final AppointmentService appointmentService;
    private final ServletContext app;

    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            user.setPassword(null);
            return user;
        }
        return null;
    }

    @GetMapping("/excel/export/patients")
    public ResponseEntity<byte[]> exportExcel() throws IOException, IllegalAccessException {
        List<PatientDTO> list = patientService.getAllPatients();
        byte[] result = excelService.exportXlsx(list, PatientDTO.class);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=patients.xlsx")
                .body(result);
    }

    @GetMapping("/patients")
    public String getAllPatient(
            Model model,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(sort = "lastModifiedAt", direction = Sort.Direction.DESC) Pageable pageable,
            Authentication authentication
    ) {
        model.addAttribute("search", search);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        Page<PatientDTO> page;
        if (search == null || search.isBlank()) {
            User user = (User) authentication.getPrincipal();
            if ((user.getRole().getId().equals("DOC"))) {
                page = patientService.getAllPatients(pageable, user.getId());
            } else {
                page = patientService.getAllPatients(pageable);
            }
        } else {
            List<PatientDTO> list = patientService.getAllPatients();
            page = searchService.search(search, list, pageable);
        }
        model.addAttribute("page", page);
        return "dashboard/patient/patients";
    }


    @GetMapping("/edit-patient/{id}")
    public String editPatient(@PathVariable Long id, Model model) {
        UpdatePatientDTO updatePatientDTO = patientService.getUpdatePatientById(id);
        model.addAttribute("insuranceProviders", InsuranceProvider.values());
        model.addAttribute("updatePatientDTO", updatePatientDTO);
        return "dashboard/patient/edit-patient";
    }

    @PostMapping("/edit-patient")
    public String editPatient(@Valid UpdatePatientDTO updatePatientDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("updatePatientDTO", updatePatientDTO);
            return "dashboard/patient/edit-patient";
        }
        try {
            Patient patient = patientService.getPatientById(updatePatientDTO.getId());
            patientService.updatePatient(patient, updatePatientDTO);
        } catch (DuplicateException e) {
            if (e.getMessage().contains("Email")) {
                bindingResult.rejectValue("email", "error.email", e.getMessage());
            } else {
                bindingResult.rejectValue("contactNumber", "error.contactNumber", e.getMessage());
            }
            model.addAttribute("updatePatientDTO", updatePatientDTO);
            return "dashboard/patient/edit-patient";
        }
        return "redirect:/dashboard/patients";
    }

    @GetMapping("/patient-detail/{id}")
    public String showPatientDetails(@PathVariable Long id, Model model) {
        Patient patientOptional = patientService.getPatientById(id);
        List<MedicalRecord> medicalRecordList = patientService.getMedicalRecordById(patientOptional);
        medicalRecordList.sort(Comparator.comparing(MedicalRecord::getCreatedAt).reversed());
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
        List<AppointmentDTO> appointments = patientService.getAppointmentByPatient(patientOptional);
        model.addAttribute("appointments", appointments);
        List<Payment> paymentList1 = paymentService.findAllPayment();
        List<Payment> paymentList = paymentService.findPaymentsForConfirmedAppointments();
        if (patientOptional != null) {
            model.addAttribute("patient", patientOptional);
            model.addAttribute("medical_record", medicalRecordList);
            model.addAttribute("paymentList", paymentList);
            model.addAttribute("medicalRecordFilesMap", medicalRecordFilesMap);
            model.addAttribute("updateMedicalRecordDTOs", updateMedicalRecordDTOS);
            model.addAttribute("paymentList1", paymentList1);
            return "dashboard/patient/patient-detail";
        } else {
            return "redirect:/dashboard/patients";
        }
    }

//    @PostMapping("/patient-detail/{patientId}/{medicalRecordId}")
//    public String updateMedicalRecord(
//            @PathVariable Long patientId,
//            @PathVariable Long medicalRecordId,
//            @ModelAttribute("updateDTO") UpdateMedicalRecordDTO updateMedicalRecordDTO,
//            @RequestParam("file") MultipartFile file
//    ) {
//        MedicalRecord medicalRecord = patientService.findMedicalId(medicalRecordId);
//        if (!file.isEmpty()) {
//            try {
//                // Lưu tệp bằng cách gọi phương thức store từ StorageService
//                String storedFilename = storageService.store(file);
//
//                // Cập nhật đường dẫn tệp trong DTO hoặc đối tượng MedicalRecord
//                updateMedicalRecordDTO.setDocumentPath(storedFilename);
//            } catch (StorageException | FileException e) {
//                // Xử lý lỗi lưu trữ
//                // Ví dụ: có thể quay trở lại trang tạo hoặc hiển thị thông báo lỗi cho người dùng
//            }
//        }
//        System.out.println(updateMedicalRecordDTO.getDiagnosis());
//        MedicalRecord updatedMedicalRecord = patientService.updateMedicalRecord(medicalRecord, updateMedicalRecordDTO);
//
//        return "dashboard/patient/patient-detail/" + patientId;
//    }

    @PostMapping("/uploads")
    public String save(Model model, @RequestParam("file") MultipartFile file) {
        String uploadRootPath = app.getRealPath("/uploads");
        File uploadRootDir = new File(uploadRootPath);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        try {
            String filename = file.getOriginalFilename();
            File serverFile = new File(uploadRootDir.getAbsoluteFile() + File.separator + filename);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(file.getBytes());
            stream.close();
            List<String> uploadedFiles = (List<String>) model.getAttribute("uploadedFiles");
            if (uploadedFiles == null) {
                uploadedFiles = new ArrayList<>();
            }
            uploadedFiles.add(filename);
            model.addAttribute("uploadedFiles", uploadedFiles);
            System.out.println(uploadedFiles.get(0));
            System.out.println(serverFile.getAbsolutePath());
        } catch (Exception e) {
        }
        return "dashboard/patient/patient-detail";
    }

    @GetMapping("/add-patient")
    public String addPatient(Model model) {
        List<InsuranceProvider> insuranceProviders = Arrays.stream(InsuranceProvider.values())
                .collect(Collectors.toList());

        model.addAttribute("insuranceProviders", insuranceProviders);

        model.addAttribute("newPatientDTO", new NewPatientDTO());
        return "dashboard/patient/add-patient";
    }

    @PostMapping("/add-patient")
    public String addPatient(@Valid NewPatientDTO newPatientDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newPatientDTO", newPatientDTO);
            return "dashboard/patient/add-patient";
        }
        try {
            patientService.createPatient(newPatientDTO);
        } catch (DuplicateException e) {
            model.addAttribute("newPatientDTO", newPatientDTO);
            return "dashboard/patient/add-patient";
        }
        return "redirect:/dashboard/patients";
    }


    @GetMapping("/add-medical-record/{patientId}")
    public String showAddMedicalRecordForm(@PathVariable Long patientId, Model model) {
        NewMedicalRecordDTO newMedicalRecordDTO = new NewMedicalRecordDTO();
        newMedicalRecordDTO.setPatientId(patientId);
        System.out.println(newMedicalRecordDTO.getPatientId());
        model.addAttribute("newMedicalRecordDTO", newMedicalRecordDTO);
        model.addAttribute("doctors", staffService.getAllDoctor());
        return "dashboard/patient/add-medical-record";
    }

    @PostMapping("/add-medical-record")
    public String addMedicalRecord(
            NewMedicalRecordDTO newMedicalRecordDTO,
            Model model,
            BindingResult result // Thêm tham số cho patientId
    ) {
        if (result.hasErrors()) {
            model.addAttribute("newMedicalRecordDTO", newMedicalRecordDTO);
            model.addAttribute("doctors", staffService.getAllDoctor());
            return "dashboard/patient/add-medical-record";
        }
        System.out.println(newMedicalRecordDTO.getPatientId());
        patientService.creMedicalRecord(newMedicalRecordDTO);
        System.out.println(newMedicalRecordDTO.getPatientId());
        if (result.hasErrors()) {
            model.addAttribute("newMedicalRecordDTO", newMedicalRecordDTO);
            model.addAttribute("doctors", staffService.getAllDoctor());
            return "dashboard/patient/add-medical-record";
        }
        return "redirect:/dashboard/patient-detail/" + newMedicalRecordDTO.getPatientId();
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("newMedicalRecordDTO", newMedicalRecordDTO);
//            return "dashboard/patient/add-medical-record";
//        }
//        System.out.println(newMedicalRecordDTO.getDiagnosis());
//
//        try {
////            if (file != null && !file.isEmpty()) {
////                String fileName = storageService.store(file);
////                newMedicalRecordDTO.setDocumentPath(fileName);
////            }
//            patientService.createMedicalRecord(newMedicalRecordDTO);
//        } catch (DuplicateException e) {
//            model.addAttribute("doctors", staffService.getAllDoctor());
//            return "dashboard/patient/add-medical-record";
//        }
//
//        return "redirect:/dashboard/patient-detail";
    }

//    @PostMapping("/sendInvoice/{patientId}/{paymentId}")
//    public String saveMedicalRecord(@PathVariable Long paymentId, @PathVariable Long patientId) {
//        Payment payment = paymentService.getPaymentById(paymentId);
//        Patient patient = patientService.getPatientById(patientId);
//
//        String subject = "Mã hóa đơn thanh toán " + paymentId;
//        String content = "Xin chào,\n\n";
//        content += "Chúng tôi xin thông báo rằng hóa đơn cho dịch vụ khám chữa bệnh của bạn đã được thanh toán thành công.\n";
//        content += "Cảm ơn bạn đã tin tưởng và sử dụng dịch vụ MedicalCare của chúng tôi!\n";
//        content += "Nếu bạn có bất kỳ câu hỏi hoặc cần hỗ trợ thêm, đừng ngần ngại liên hệ chúng tôi.\n\n";
//        content += "Hóa đơn chi tiết được đính kèm bên dưới\n";
//        content += "Trân trọng,\nMedicalCare Team";
//
//
//        try {
//            Resource invoiceResource = storageService.loadAsResource("Invoice-1.pdf");
//            InputStream inputStream = invoiceResource.getInputStream();
//
//            // Gửi hóa đơn qua email
//            emailService.sendInvoiceAsAttachment(subject, content, patient.getEmail(), "Invoice-1.pdf", inputStream);
//            System.out.println(patient.getEmail());
//            // Chuyển hướng hoặc trả về view tùy theo logic của bạn
//            return "redirect:/dashboard/patients";
//        } catch (IOException e) {
//            // Xử lý ngoại lệ khi không thể tải tệp
//            // Hoặc hiển thị thông báo lỗi cho người dùng
//            return "redirect:/error";
//        }
//    }

    @GetMapping("/invoice/{paymentId}")
    public String processPayment(@PathVariable Long paymentId, Model model) {
        Payment payment = paymentService.getPaymentById(paymentId);
        Patient patient = payment.getAppointment().getPatient();
        double VAT = payment.getAppointment().getService().getPrice() * 0.1;
        double totalCost = payment.getAppointment().getService().getPrice();

        model.addAttribute("patient", patient);
        model.addAttribute("payment", payment);
        model.addAttribute("VAT", VAT);
        model.addAttribute("totalPrice", totalCost);

        return "dashboard/patient/invoice"; // Trả về trang payment.html
    }

    // Trang hiển thị thông tin thanh toán
    @GetMapping("/payment")
    public String showPaymentForm() {
        return "dashboard/patient/invoice"; // Trang hiển thị thông tin thanh toán (payment.html)
    }

    @GetMapping("/success")
    public String success() {
        return "dashboard/patient/success"; // Trang hiển thị thông tin thanh toán (payment.html)
    }

}