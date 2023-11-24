package com.example.project.admin.schedule;

import com.example.project.admin.appointment.Appointment;
import com.example.project.admin.appointment.AppointmentRepository;
import com.example.project.admin.user.User;
import com.example.project.admin.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Override
    public List<LocalDateTime> getAllAvailableScheduleOfDoctor(User doctor, LocalDate date) {
        if (date == null) {
            date = LocalDate.now().plusDays(1);
        }
        LocalDateTime startDate = date.isEqual(LocalDate.now())
                ? LocalDateTime.now().plusHours(1)
                : date.atStartOfDay();
        List<DoctorSchedule> schedules = doctorScheduleRepository
                .findAllByDoctorAndTimeStartBetweenOrderByTimeStartAsc(doctor, startDate, date.atStartOfDay().plusDays(1));
        List<Appointment> appointments = appointmentRepository.findAllByTimeDateAndDoctor(date, doctor.getId());
        List<LocalDateTime> times = new ArrayList<>();
        for (DoctorSchedule schedule : schedules) {
            LocalDateTime current = schedule.getTimeStart();
            while (current.isBefore(schedule.getTimeEnd().minusMinutes(29))) {
                final LocalDateTime finalCurrent = current;
                long count = appointments.stream()
                        .filter(appointment -> appointment.getTime().equals(finalCurrent))
                        .count();
                if (count == 0) {
                    times.add(current);
                }
                current = current.plusMinutes(30);
            }
        }
        return times;
    }

    @Override
    public List<LocalDate> getAvailableDateOfDoctor(User doctor) {
        List<DoctorSchedule> schedules = doctorScheduleRepository
                .findAllByDoctorAndTimeStartAfterOrderByTimeStartAsc(doctor, LocalDate.now().atStartOfDay());
        return schedules.stream()
                .map(schedule -> schedule.getTimeStart().toLocalDate())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public void saveSchedule(Long userId, List<String> time, LocalDate firstDayOfWeek) {
        User doctor = userRepository.findById(userId).orElse(null);
        List<DoctorSchedule> oldSchedules = doctorScheduleRepository
                .findAllByDoctorAndTimeStartBetweenOrderByTimeStartAsc(
                        doctor, firstDayOfWeek.atStartOfDay(),
                        firstDayOfWeek.plusDays(7).atStartOfDay()
                );
        List<DoctorSchedule> newSchedules = new ArrayList<>();
        for (String s : time) {
            String[] parts = s.split(": ");
            int dayOfWeek = Integer.parseInt(parts[0]);
            String[] timeParts = parts[1].split(" - ");
            LocalTime startTime = LocalTime.parse(timeParts[0]);
            LocalTime endTime = LocalTime.parse(timeParts[1]);

            LocalDate scheduleDate = firstDayOfWeek.plusDays(dayOfWeek - 1);
            LocalDateTime startDateTime = scheduleDate.atTime(startTime);
            LocalDateTime endDateTime = scheduleDate.atTime(endTime);

            DoctorSchedule schedule = new DoctorSchedule();
            schedule.setTimeStart(startDateTime);
            schedule.setTimeEnd(endDateTime);

            schedule.setDoctor(doctor);
            newSchedules.add(schedule);
        }

        boolean invalidDate = oldSchedules.stream().filter(
                s -> newSchedules.stream().noneMatch(
                        oldSchedule -> oldSchedule.getTimeStart().equals(s.getTimeStart())
                                && oldSchedule.getTimeEnd().equals(s.getTimeEnd())
                )
        ).anyMatch(s -> s.getTimeStart().isBefore(LocalDate.now().atStartOfDay()));

        if (invalidDate) {
            throw new IllegalStateException("Không thể thay đổi lịch làm việc đã qua");
        }

        List<Appointment> existAppointment = new ArrayList<>();
        oldSchedules.stream().filter(
                s -> newSchedules.stream().noneMatch(
                        oldSchedule -> oldSchedule.getTimeStart().equals(s.getTimeStart())
                                && oldSchedule.getTimeEnd().equals(s.getTimeEnd())
                )
        ).forEach(s ->
                existAppointment.addAll(
                        appointmentRepository.findAllAppointment(s.getTimeStart(), s.getTimeEnd(), userId)
                )
        );
        if (!existAppointment.isEmpty()) {
            throw new IllegalStateException("Lịch làm việc đã có lịch hẹn, không thể xóa");
        }
        doctorScheduleRepository.deleteAll(oldSchedules);
        doctorScheduleRepository.saveAll(newSchedules);
    }

    @Override
    public List<String> getSchedule(User user, LocalDate firstDayOfWeek) {
        List<DoctorSchedule> schedules = doctorScheduleRepository
                .findAllByDoctorAndTimeStartBetweenOrderByTimeStartAsc(
                        user, firstDayOfWeek.atStartOfDay(),
                        firstDayOfWeek.plusDays(7).atStartOfDay()
                );
        List<String> times = new ArrayList<>();
        // return list of time with format s has the form 1: 07:00 - 09:30, with 1 is the day of week (1 is Monday, 2 is Tuesday, etc.) and start in 07:00 and end in 09:30
        for (DoctorSchedule schedule : schedules) {
            LocalDateTime start = schedule.getTimeStart();
            LocalDateTime end = schedule.getTimeEnd();
            times.add(
                    start.getDayOfWeek().getValue()
                            + ": " + String.format("%02d", start.getHour()) + ":" + String.format("%02d", start.getMinute())
                            + " - " + String.format("%02d", end.getHour()) + ":" + String.format("%02d", end.getMinute())
            );
        }
        return times;
    }
}
