package com.example.project.admin.department;

import com.example.project.admin.department.dto.DepartmentDTO;
import com.example.project.admin.department.dto.NewDepartmentDTO;
import com.example.project.admin.department.dto.UpdateDepartmentDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {
    Department createDepartment(NewDepartmentDTO newDepartment);

    Department getDepartmentById(Long id);

    // Page<DepartmentDTO> getAllDepartment(Pageable pageable);
    List<Department> getAllDepartments();

    Department updateDepartment(Department department, UpdateDepartmentDTO updateDepartmentDTO);

    UpdateDepartmentDTO getUpdateDepartmentById(Long id);

    List<String> getAllDepartmentId();

    List<Department> searchDepartment(String keyword);

    Page<Department> getAllDepartments(Integer pageNo, Integer numberPage);

    List<DepartmentDTO> getAllDepartmentDTOs();

    List<DepartmentDTO> getTop5Departments();
}
