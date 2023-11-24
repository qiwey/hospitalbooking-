package com.example.project.admin.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", length = 5)
    private String id;

    @Column(name = "role_name", length = 50)
    private String name;

//    @Column(name = "role_desc")
//    private String description;
//
//    @Column(name = "is_active")
//    private Boolean activeStatus = true;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    @Column(name = "last_modified_at")
//    private LocalDateTime lastModifiedAt = LocalDateTime.now();
}
