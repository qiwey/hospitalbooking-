package com.example.project.admin.department;

import com.example.project.admin.department.dto.DepartmentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByCode(String code);

    @Query("SELECT d.id FROM Department d")
    List<String> findAllDepartmentName();

    @Query("SELECT d FROM Department d WHERE d.name LIKE %?1%")
    List<Department> searchDepartment(String keyword);

    @Query("""
            SELECT new com.example.project.admin.department.dto.DepartmentDTO
            (
                d.id,
                d.code,
                d.name,
                d.fullDescription,
                d.activeStatus,
                d.lastModifiedAt
            ) FROM Department d
            WHERE d.activeStatus = true
            ORDER BY d.lastModifiedAt DESC
            """)
    List<DepartmentDTO> findTop5Departments();

    @Query("""
            SELECT new com.example.project.admin.department.dto.DepartmentDTO
            (
                d.id,
                d.code,
                d.name,
                d.shortDescription,
                d.activeStatus,
                d.lastModifiedAt
            ) FROM Department d
            """)
    List<DepartmentDTO> findAllDepartmentDTOs();
}