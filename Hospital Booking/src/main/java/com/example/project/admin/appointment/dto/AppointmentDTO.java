package com.example.project.admin.appointment.dto;

import com.example.project.base.constant.BookingStatus;
import com.example.project.base.constant.PaymentStatus;
import com.example.project.util.search.Searchable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentDTO implements Searchable {
    private Long id;
    private Long patientId;
    private String patientName;
    private int patientAge;
    private String contactNumber;
    private String serviceName;
    private String doctorName;
    private String reason;
    private String departmentName;
    private LocalDateTime time;
    private BookingStatus status;
    private PaymentStatus paymentStatus;
    private Long paymentId;
    private LocalDateTime createdAt;

    @Override
    public String getSearchableString() {
        return patientName + " "
                + contactNumber + " "
                + serviceName + " "
                + doctorName + " "
                + reason + " "
                + departmentName;
    }
}
