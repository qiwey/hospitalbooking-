package com.example.project.admin.appointment;

import com.example.project.admin.appointment.dto.AppointmentDTO;
import com.example.project.admin.user.User;
import com.example.project.home.appointment.NewAppointmentDTO;
import com.example.project.base.constant.BookingStatus;
import com.example.project.admin.service.DoctorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    Page<AppointmentDTO> getAllAppointmentsWithDate(User user, LocalDate startDate, LocalDate endDate, Pageable pageable);

    List<AppointmentDTO> getAllAppointmentsWithDate(User user, LocalDate startDate, LocalDate endDate);

    Appointment createAppointment(DoctorService service, NewAppointmentDTO newAppointmentDTO);

    Appointment getAppointmentById(Long id);

    void updateStatus(Appointment appointment, BookingStatus status);

    void sendEmailAppointment(Appointment appointment, BookingStatus status);
    List<Appointment> getConfirmedAppointments();
}
