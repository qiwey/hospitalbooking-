package com.example.project.admin.feedback;

import com.example.project.admin.appointment.Appointment;
import com.example.project.admin.appointment.AppointmentRepository;
import com.example.project.admin.appointment.AppointmentService;
import com.example.project.admin.department.Department;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Override
    public void createFeedback(Appointment appointment, String comment) {
        Feedback feedback = new Feedback();
        feedback.setAppointment(appointment);
        feedback.setComment(comment);
        feedbackRepository.save(feedback);
    }

    @Override
    public Page<Feedback> getFeedbacks(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

    @Override
    public Page<Feedback> getFeedbacksService(Pageable pageable, Long serviceId) {
        return feedbackRepository.findAllByAppointment_Service_Id(serviceId, pageable);
    }

    @Override
    public Page<Feedback> getFeedbacksService(Pageable pageable, Department department) {
        return feedbackRepository.findAllByAppointment_Service_Doctor_Department_Id(department.getId(), pageable);
    }
}
