package com.example.project.admin.staff;

import com.example.project.admin.service.DoctorServiceRepository;
import com.example.project.admin.user.User;
import com.example.project.admin.staff.dto.UpdateStaffDTO;
import com.example.project.admin.user.Role;
import com.example.project.admin.user.UserRepository;
import com.example.project.admin.department.Department;
import com.example.project.admin.department.DepartmentRepository;
import com.example.project.base.exception.DuplicateException;
import com.example.project.base.exception.NotFoundException;
import com.example.project.admin.staff.dto.NewStaffDTO;
import com.example.project.admin.staff.dto.StaffDTO;
import com.example.project.util.storage.StorageService;
import com.example.project.util.UtilHelper;
import lombok.RequiredArgsConstructor;
import com.example.project.admin.user.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final DepartmentRepository departmentRepository;
    private final DoctorServiceRepository doctorServiceRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;

    @Override
    public Page<StaffDTO> getAllStaffs(Pageable pageable) {
        return userRepository.findAllStaff(pageable);
    }

    @Override
    public List<StaffDTO> getAllStaffs() {
        return userRepository.findAllStaff();
    }

    @Override
    public List<StaffDTO> getAllStaffs(String roleId, Long departmentId, Sort sort) {
        return userRepository.findAllStaffByWithFilter(roleId, departmentId, sort);
    }

    @Override
    @Transactional
    public User createStaff(NewStaffDTO newStaff) {
        if (userRepository.existsByEmail(newStaff.getEmail())) {
            throw new DuplicateException("Email đã tồn tại");
        }
        if (userRepository.existsByContactNumber(newStaff.getContactNumber())) {
            throw new DuplicateException("Số điện thoại đã tồn tại");
        }
        Role role = roleRepository.findById(newStaff.getRoleId()).orElseThrow(
                () -> new NotFoundException("Vai trò không tồn tại")
        );
        Department department = departmentRepository.findById(newStaff.getDepartmentId()).orElseThrow(
                () -> new NotFoundException("Phòng ban không tồn tại")
        );
        User user = mapper.map(newStaff, User.class);
        user.setDepartment(department);
        user.setRole(role);
        String newUsername;
        do {
            newUsername = UtilHelper.toSlugString(newStaff.getFirstName()) + UtilHelper.getRandomNumber(4);
        } while (userRepository.existsByUsername(newUsername));
        if (newStaff.getPicture() != null && !newStaff.getPicture().isEmpty()) {
            user.setProfilePicture(storageService.store(newStaff.getPicture()));
        }
        user.setUsername(newUsername);
        user.setPassword(passwordEncoder.encode(newStaff.getContactNumber()));
        user.setProfilePicture(storageService.store(newStaff.getPicture()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateStaff(User user, UpdateStaffDTO updateStaff) {
        if (!user.getEmail().equals(updateStaff.getEmail())
                && userRepository.existsByEmail(updateStaff.getEmail())) {
            throw new DuplicateException("Email đã tồn tại");
        }
        if (!user.getContactNumber().equals(updateStaff.getContactNumber())
                && userRepository.existsByContactNumber(updateStaff.getContactNumber())) {
            throw new DuplicateException("Số điện thoại đã tồn tại");
        }
        Role role = roleRepository.findById(updateStaff.getRoleId()).orElseThrow(
                () -> new NotFoundException("Vai trò không tồn tại")
        );
        Department department = departmentRepository.findById(updateStaff.getDepartmentId()).orElseThrow(
                () -> new NotFoundException("Phòng ban không tồn tại")
        );
        mapper.map(updateStaff, user);
        user.setRole(role);
        user.setDepartment(department);
        if (updateStaff.getPicture() != null && !updateStaff.getPicture().isEmpty()) {
            String profilePicture = storageService.store(updateStaff.getPicture());
            storageService.delete(user.getProfilePicture());
            user.setProfilePicture(profilePicture);
        }
        return userRepository.save(user);
    }

    @Override
    public User getStaffById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Nhân viên không tồn tại"));
    }

    @Override
    public List<StaffDTO> getAllDoctor() {
        return userRepository.findByRoleId("DOC");
    }

    @Override
    public List<StaffDTO> getTop4Doctor() {
        List<StaffDTO> doctors = userRepository.findTop4Doctor();
        doctors.forEach(doctor -> {
            doctor.setMaxPrice(doctorServiceRepository.findMaxPriceByDoctorId(doctor.getUserId()));
            doctor.setMinPrice(doctorServiceRepository.findMinPriceByDoctorId(doctor.getUserId()));
        });
        return doctors;
    }

    @Override
    public UpdateStaffDTO getUpdateStaffById(Long id) {
        User user = getStaffById(id);
        UpdateStaffDTO updateStaffDTO = mapper.map(user, UpdateStaffDTO.class);
        updateStaffDTO.setDepartmentId(user.getDepartment().getId());
        updateStaffDTO.setRoleId(user.getRole().getId());
        return updateStaffDTO;
    }

    @Override
    public Page<StaffDTO> getAllHomeDoctor(Pageable pageable) {
        Page<StaffDTO> doctors = userRepository.findByRoleId("DOC", pageable);
        doctors.forEach(doctor -> {
            doctor.setMaxPrice(doctorServiceRepository.findMaxPriceByDoctorId(doctor.getUserId()));
            doctor.setMinPrice(doctorServiceRepository.findMinPriceByDoctorId(doctor.getUserId()));
        });
        return doctors;
    }
}
