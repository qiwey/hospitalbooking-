package com.example.project.admin.schedule;

import com.example.project.admin.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    List<DoctorSchedule> findAllByDoctorAndTimeStartBetweenOrderByTimeStartAsc(User doctor, LocalDateTime start, LocalDateTime end);

    List<DoctorSchedule> findAllByDoctorAndTimeStartAfterOrderByTimeStartAsc(User doctor, LocalDateTime start);
}
