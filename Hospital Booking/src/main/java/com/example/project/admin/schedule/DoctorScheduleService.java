package com.example.project.admin.schedule;

import com.example.project.admin.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DoctorScheduleService {
    List<LocalDateTime> getAllAvailableScheduleOfDoctor(User doctor, LocalDate date);

    List<LocalDate> getAvailableDateOfDoctor(User doctor);

    void saveSchedule(Long userId, List<String> time, LocalDate firstDayOfWeek);

    List<String> getSchedule(User user, LocalDate firstDayOfWeek);
}
