package com.example.project.admin.service;

import com.example.project.admin.service.dto.ServiceDTO;
import com.example.project.admin.service.dto.DoctorServiceHomeDTO;
import com.example.project.home.service.ServiceHomeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorServiceRepository extends JpaRepository<DoctorService, Long> {
    boolean existsByName(String name);

    DoctorService findTop1ByDoctorId(Long doctorId);

    @Query("SELECT ds FROM DoctorService ds WHERE ds.name LIKE %?1%")
    List<DoctorService> searchService(String keyword);

    Page<DoctorService> findAllByNameLike(String name, Pageable pageable);


    @Query("""
                SELECT new com.example.project.admin.service.dto.ServiceDTO
                (
                    ds.id,
                    ds.name,
                    ds.shortDescription,
                    ds.type,
                    ds.quality,
                    CONCAT(ds.doctor.lastName,' ', ds.doctor.firstName),
                    ds.doctor.department.name,
                    ds.price,
                    ds.lastModifiedAt
                )
                FROM DoctorService ds
            """)
    List<ServiceDTO> findAllServiceDTOs();

//    List<ServiceHomepageDTO> findAllByQuality(ServiceQuality quality);

//    Page<ServiceHomepageDTO> findAllServices(Pageable pageable);

//    @Query(value = """
//            SELECT new com.example.project.service.dto.ServiceHomepageDTO
//            (
//                u.user_id,
//                CONCAT(u.last_name, ' ', u.first_name)
//            )
//            FROM User u
//            LEFT JOIN u.doctor_service ds ON u.user_id = ds.service_id
//            WHERE ()
//            """)
//    List<ServiceHomepageDTO> findAllByWithFilter();

    @Query("""
            SELECT new com.example.project.home.service.ServiceHomeDTO
            (
                ds.id,
                ds.name,
                ds.shortDescription,
                ds.type,
                ds.quality,
                CONCAT(ds.doctor.lastName, ' ', ds.doctor.firstName),
                ds.doctor.department.name,
                ds.price,
                ds.createdAt
            )
            FROM DoctorService ds
            WHERE (:search IS NULL OR :search = '' OR ds.name LIKE %:search%)
            """)
    Page<ServiceHomeDTO> findServiceHomeDTO(Pageable pageable, String search);

    @Query("""
            SELECT new com.example.project.admin.service.dto.DoctorServiceHomeDTO
            (
                ds.id,
                ds.name,
                ds.shortDescription,
                CONCAT(ds.doctor.lastName, ' ', ds.doctor.firstName),
                ds.type,
                ds.quality,
                ds.doctor.department.name,
                ds.price,
                ds.lastModifiedAt
            )
            FROM DoctorService ds
            """)
    List<DoctorServiceHomeDTO> findAllServiceHomeDTOs();

    @Query("""
                SELECT MAX(ds.price)
                FROM DoctorService ds
                WHERE ds.doctor.id = :userId
            """)
    Double findMaxPriceByDoctorId(Long userId);

    @Query("""
                SELECT MIN(ds.price)
                FROM DoctorService ds
                WHERE ds.doctor.id = :userId
            """)
    Double findMinPriceByDoctorId(Long userId);
}
