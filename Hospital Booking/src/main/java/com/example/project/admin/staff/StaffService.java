package com.example.project.admin.staff;

import com.example.project.admin.user.User;
import com.example.project.admin.staff.dto.NewStaffDTO;
import com.example.project.admin.staff.dto.UpdateStaffDTO;
import com.example.project.admin.staff.dto.StaffDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface StaffService {
    Page<StaffDTO> getAllStaffs(Pageable pageable);

    List<StaffDTO> getAllStaffs();

    List<StaffDTO> getAllStaffs(String roleId, Long departmentId, Sort sort);

    User createStaff(NewStaffDTO newStaff);

    User updateStaff(User user, UpdateStaffDTO updateStaff);

    User getStaffById(Long id);

    List<StaffDTO> getAllDoctor();

    List<StaffDTO> getTop4Doctor();

    UpdateStaffDTO getUpdateStaffById(Long id);

    Page<StaffDTO> getAllHomeDoctor(Pageable pageable);
}
