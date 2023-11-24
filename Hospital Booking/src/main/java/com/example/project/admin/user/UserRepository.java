package com.example.project.admin.user;

import com.example.project.admin.staff.dto.StaffDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query("select count(u) from User u where u.role.id = ?1")
    long countByRole_Id(String id);

    @Query(value = """
            SELECT new com.example.project.admin.staff.dto.StaffDTO
            (
                u.id,
                CONCAT(u.lastName, ' ', u.firstName),
                u.email,
                u.contactNumber,
                YEAR(CURRENT_DATE) - YEAR(u.dateOfBirth),
                u.gender,
                u.description,
                u.role.name,
                u.department.name,
                u.lockedStatus,
                u.lastModifiedAt,
                u.profilePicture
            )
            FROM User u
            """
    )
    Page<StaffDTO> findAllStaff(Pageable pageable);

    @Query(value = """
            SELECT new com.example.project.admin.staff.dto.StaffDTO
            (
                u.id,
                CONCAT(u.lastName, ' ', u.firstName),
                u.email,
                u.contactNumber,
                YEAR(CURRENT_DATE) - YEAR(u.dateOfBirth),
                u.gender,
                u.description,
                u.role.name,
                u.department.name,
                u.lockedStatus,
                u.lastModifiedAt,
                u.profilePicture
            )
            FROM User u
            """
    )
    List<StaffDTO> findAllStaff();

    @Query(value = """
            SELECT new com.example.project.admin.staff.dto.StaffDTO
            (
                u.id,
                CONCAT(u.lastName, ' ', u.firstName),
                u.email,
                u.contactNumber,
                YEAR(CURRENT_DATE) - YEAR(u.dateOfBirth),
                u.gender,
                u.description,
                u.role.name,
                u.department.name,
                u.lockedStatus,
                u.lastModifiedAt,
                u.profilePicture
            )
            FROM User u
            LEFT JOIN u.role r
            LEFT JOIN u.department d
            WHERE (:roleId IS NULL OR :roleId = '' OR u.role.id = :roleId)
            AND (:departmentId IS NULL OR u.department.id = :departmentId)
            """
    )
    List<StaffDTO> findAllStaffByWithFilter(
            @Param("roleId") String roleId,
            @Param("departmentId") Long departmentId,
            Sort sort
    );

    @Query(value = """
            SELECT new com.example.project.admin.staff.dto.StaffDTO
            (
                u.id,
                CONCAT(u.lastName, ' ', u.firstName),
                u.email,
                u.contactNumber,
                YEAR(CURRENT_DATE) - YEAR(u.dateOfBirth),
                u.gender,
                u.description,
                u.role.name,
                u.department.name,
                u.lockedStatus,
                u.lastModifiedAt,
                u.profilePicture
            )
            FROM User u
            ORDER BY u.createdAt DESC
            LIMIT 4
            """
    )
    List<StaffDTO> findTop4Doctor();

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByContactNumber(String contactNumber);

    @Query(value = """
            SELECT new com.example.project.admin.staff.dto.StaffDTO
            (
                u.id,
                CONCAT(u.lastName, ' ', u.firstName),
                u.email,
                u.contactNumber,
                YEAR(CURRENT_DATE) - YEAR(u.dateOfBirth),
                u.gender,
                u.description,
                u.role.name,
                u.department.name,
                u.lockedStatus,
                u.lastModifiedAt,
                u.profilePicture
            )
            FROM User u
            WHERE u.role.id = :id
            """
    )
    List<StaffDTO> findByRoleId(String id);

    @Query(value = """
            SELECT new com.example.project.admin.staff.dto.StaffDTO
            (
                u.id,
                CONCAT(u.lastName, ' ', u.firstName),
                u.email,
                u.contactNumber,
                YEAR(CURRENT_DATE) - YEAR(u.dateOfBirth),
                u.gender,
                u.description,
                u.role.name,
                u.department.name,
                u.lockedStatus,
                u.lastModifiedAt,
                u.profilePicture
            )
            FROM User u
            WHERE u.role.id = :id
            """
    )
    Page<StaffDTO> findByRoleId(String id, Pageable pageable);
}
