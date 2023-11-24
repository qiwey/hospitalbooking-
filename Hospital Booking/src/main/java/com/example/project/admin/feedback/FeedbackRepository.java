package com.example.project.admin.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Page<Feedback> findAllByAppointment_Service_Id(Long serviceId, Pageable pageable);

    Page<Feedback> findAllByAppointment_Service_Doctor_Department_Id(Long id, Pageable pageable);
}
