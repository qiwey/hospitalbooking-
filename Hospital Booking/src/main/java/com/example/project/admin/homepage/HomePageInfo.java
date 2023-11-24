package com.example.project.admin.homepage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "homepage_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomePageInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "homepage_id")
    private Long id;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "about_us_img")
    private String aboutUsImg;

    @Column(name = "about_us_title")
    private String aboutUsTitle;

    @Column(name = "about_us_desc", columnDefinition = "MEDIUMTEXT")
    @Lob
    private String aboutUsDesc;

    @Column(name = "department_title")
    private String departmentTitle;

    @Column(name = "department_desc", columnDefinition = "MEDIUMTEXT")
    @Lob
    private String departmentDesc;

    @Column(name = "doctor_title")
    private String doctorTitle;

    @Column(name = "doctor_desc", columnDefinition = "MEDIUMTEXT")
    @Lob
    private String doctorDesc;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "last_modified_at")
    private Long lastModifiedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = System.currentTimeMillis();
        this.lastModifiedAt = System.currentTimeMillis();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedAt = System.currentTimeMillis();
    }
}
