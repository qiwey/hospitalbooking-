package com.example.project.admin.payment;

import java.util.List;

public interface PaymentService {
    Payment getPaymentById(Long id);

    List<Payment> findPaymentsForConfirmedAppointments();

    List<Payment> findAllPayment();

    void updatePayment(Payment payment);
//  List<Payment> findPaymentsForConfirmedAppointmentsByPatientId(Long patientId);
}
