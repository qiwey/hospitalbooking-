package com.example.project.admin.feedback;

import com.example.project.admin.appointment.Appointment;
import com.example.project.admin.department.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {
    void createFeedback(Appointment appointment, String comment);

    Page<Feedback> getFeedbacks(Pageable pageable);

    Page<Feedback> getFeedbacksService(Pageable pageable, Long serviceId);

    Page<Feedback> getFeedbacksService(Pageable pageable, Department department);
}
