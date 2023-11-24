package com.example.project.admin.appointment;

import com.example.project.admin.appointment.dto.AppointmentDTO;
import com.example.project.admin.payment.Payment;
import com.example.project.admin.user.User;
import com.example.project.base.constant.PaymentStatus;
import com.example.project.home.appointment.NewAppointmentDTO;
import com.example.project.base.constant.BookingStatus;
import com.example.project.base.exception.NotFoundException;
import com.example.project.admin.patient.Patient;
import com.example.project.admin.patient.PatientRepository;
import com.example.project.admin.service.DoctorService;
import com.example.project.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final EmailService emailService;
    private final SpringTemplateEngine templateEngine;

    @Override
    public Page<AppointmentDTO> getAllAppointmentsWithDate(User user, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        LocalDateTime start = startDate == null ? LocalDateTime.now() : startDate.atStartOfDay();
        LocalDateTime end = endDate == null ? LocalDateTime.now().plusDays(3) : endDate.atStartOfDay().plusDays(7);
        log.error("{}", user.getRole().getId());
        log.error("{}", start);
        log.error("{}", end);
        return switch (user.getRole().getId()) {
            case "DOC" ->
                    appointmentRepository.findAllAppointmentsWithDateAndDoctor(user.getId(), start, end, pageable);
            case "ASSIS" ->
                    appointmentRepository.findAllAppointmentsWithDateAndDepart(user.getDepartment().getId(), start, end, pageable);
            default -> appointmentRepository.findAllAppointmentsWithDate(start, end, pageable);
        };
    }

    @Override
    public List<AppointmentDTO> getAllAppointmentsWithDate(User user, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate == null ? LocalDateTime.now() : startDate.atStartOfDay();
        LocalDateTime end = endDate == null ? LocalDateTime.now().plusDays(1) : endDate.atStartOfDay().plusDays(1);
        log.error("{}", user.getRole().getId());
        return switch (user.getRole().getId()) {
            case "DOC" ->
                    appointmentRepository.findAllAppointmentsWithDateAndDoctor(user.getId(), start, end, null).toList();
            case "ASSIS" ->
                    appointmentRepository.findAllAppointmentsWithDateAndDepart(user.getDepartment().getId(), start, end, null).toList();
            default -> appointmentRepository.findAllAppointmentsWithDate(start, end, null).toList();
        };
    }

    @Override
    public Appointment createAppointment(DoctorService service, NewAppointmentDTO newAppointmentDTO) {
        Patient patient = patientRepository.findByNationalIdCard(newAppointmentDTO.getPatientNationalIdCard())
                .orElse(null);
        if (patient == null) {
            patient = Patient.builder()
                    .fullName(newAppointmentDTO.getPatientName())
                    .address(newAppointmentDTO.getPatientAddress())
                    .dateOfBirth(newAppointmentDTO.getPatientDateOfBirth())
                    .gender(newAppointmentDTO.getPatientGender())
                    .nationalIdCard(newAppointmentDTO.getPatientNationalIdCard())
                    .insuranceProvider(newAppointmentDTO.getInsuranceProvider())
                    .build();
        }
        patient.setEmail(newAppointmentDTO.getPatientEmail());
        patient.setContactNumber(newAppointmentDTO.getPatientContactNumber());
        patient = patientRepository.save(patient);
        Payment payment = Payment.builder()
                .paymentStatus(PaymentStatus.UNPAID)
                .amount(service.getPrice())
                .build();
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .payment(payment)
                .service(service)
                .reason(newAppointmentDTO.getReason())
                .time(newAppointmentDTO.getTime())
                .build();
        payment.setAppointment(appointment);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not found appointment")
        );
    }

    @Override
    public void updateStatus(Appointment appointment, BookingStatus status) {
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }

    @Override
    public void sendEmailAppointment(Appointment appointment, BookingStatus status) {
        String email = appointment.getPatient().getEmail();
        String time = appointment.getTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        String content;
        switch (status) {
            case CONFIRMED:
                content = "Lịch hẹn của bạn vào lúc " + time + " đã được xác nhận";
                emailService.send("Xác nhận lịch hẹn", content, email);
                break;
            case REJECTED:
                content = "Lịch hẹn của bạn vào lúc " + time + " đã bị từ chối";
                emailService.send("Từ chối lịch hẹn", content, email);
                break;
            case DONE:
                content = "Lịch hẹn của bạn vào lúc " + time + " đã được hoàn thành\n"
                        + "Đánh giá dịch vụ tại đây: http://localhost:8080/danh-gia?appointmentId=" + appointment.getId();
                emailService.send("Đánh giá dịch vụ", content, email);
                break;
        }
    }

    @Override
    public List<Appointment> getConfirmedAppointments() {
        BookingStatus status = BookingStatus.CONFIRMED;
        return appointmentRepository.findAppointmentsByStatus(status);
    }
}
