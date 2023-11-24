package com.example.project.admin.staff.dto;

import com.example.project.base.constant.Gender;
import com.example.project.util.search.Searchable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StaffDTO implements Searchable {
    private Long userId;
    private String fullName;
    private String email;
    private Double maxPrice;
    private Double minPrice;
    private String contactNumber;
    private Integer age;
    private Gender gender;
    private String description;
    private String roleName;
    private String departmentName;
    private Boolean lockedStatus;
    private LocalDateTime lastModifiedAt;
    private String profilePicture;

    public StaffDTO(Long userId, String fullName, String email, String contactNumber, Integer age, Gender gender, String description, String roleName, String departmentName, Boolean lockedStatus, LocalDateTime lastModifiedAt, String profilePicture) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.age = age;
        this.gender = gender;
        this.description = description;
        this.roleName = roleName;
        this.departmentName = departmentName;
        this.lockedStatus = lockedStatus;
        this.lastModifiedAt = lastModifiedAt;
        this.profilePicture = profilePicture;
    }

    @Override
    public String getSearchableString() {
        return fullName + " " + description + " " + departmentName + " " + email + " " + contactNumber;
    }
}
