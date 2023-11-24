package com.example.project.admin.payment;


import com.example.project.admin.appointment.Appointment;
import com.example.project.admin.appointment.AppointmentRepository;
import com.example.project.base.constant.BookingStatus;
import com.example.project.base.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImp implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new NotFoundException("Payment not found"));
    }

    public List<Payment> findPaymentsForConfirmedAppointments() {
        // Fetch all confirmed appointments
        List<Appointment> confirmedAppointments = appointmentRepository.findAppointmentsByStatus(BookingStatus.CONFIRMED);

        // Extract IDs of confirmed appointments
        List<Long> confirmedAppointmentIds = confirmedAppointments.stream()
                .map(Appointment::getId)
                .toList(); // Requires Java 16 or later, or use .collect(Collectors.toList()) in earlier versions

        // Fetch payments for the confirmed appointments
        return paymentRepository.findByAppointmentIdIn(confirmedAppointmentIds);
    }

    @Override
    public List<Payment> findAllPayment() {
        return paymentRepository.findAll();
    }

    @Override
    public void updatePayment(Payment payment) {
        paymentRepository.save(payment);
    }
//    @Override
//    public List<Payment> findPaymentsForConfirmedAppointmentsByPatientId(Long patientId) {
//        List<Appointment> confirmedAppointments = appointmentRepository.findConfirmedAppointmentsByPatientId(patientId);
//
//        List<Long> confirmedAppointmentIds = confirmedAppointments.stream()
//                .map(Appointment::getId)
//                .collect(Collectors.toList());
//
//        return paymentRepository.findByAppointmentIdIn(confirmedAppointmentIds);
//
//
//    }
}
