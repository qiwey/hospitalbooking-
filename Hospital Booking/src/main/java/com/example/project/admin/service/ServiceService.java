package com.example.project.admin.service;

import com.example.project.admin.service.dto.NewServiceDTO;
import com.example.project.admin.service.dto.ServiceDTO;
import com.example.project.admin.service.dto.UpdateServiceDTO;
import com.example.project.admin.service.dto.DoctorServiceHomeDTO;
import com.example.project.home.service.ServiceHomeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceService {

    DoctorService createService(NewServiceDTO newServiceDTO);

    DoctorService getServiceById(Long id);

    Page<ServiceDTO> getAllServices(Pageable pageable);
    // sử dụng Pageable để lấy các thông tin về phân trang
    // như size (kích thước mỗi trang), page (trang số bao nhiêu)
    // kiểu trả về là Page<> tương tự List<>
    // nhưng có nhiều thông tin hơn cho việc phân trang
    // trả về danh sách dto thay vì cả một entity

    List<ServiceDTO> getAllServices();

    DoctorService updateService(DoctorService service, UpdateServiceDTO updateServiceDTO);

    UpdateServiceDTO getUpdateServiceById(Long id);

    Page<ServiceDTO> searchService(String keyword, Pageable pageNo);

    Page<ServiceHomeDTO> getAllHomepageServices(Pageable page, String search);

    List<DoctorServiceHomeDTO> getAllServiceHome();

    DoctorService getTop1ServiceByDoctorId(Long doctorId);

    Page<DoctorService> getAllServicesPage(Pageable pageable);

    List<DoctorService> getAllServicesPage();
}
