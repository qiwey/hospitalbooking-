package com.example.project.admin.service;

import com.example.project.admin.service.dto.NewServiceDTO;
import com.example.project.admin.service.dto.ServiceDTO;
import com.example.project.admin.service.dto.UpdateServiceDTO;
import com.example.project.admin.user.User;
import com.example.project.admin.service.dto.DoctorServiceHomeDTO;
import com.example.project.base.exception.DuplicateException;
import com.example.project.base.exception.NotFoundException;
import com.example.project.home.service.ServiceHomeDTO;
import com.example.project.admin.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServiceServiceImpl implements ServiceService {

    private final ModelMapper mapper;
    private final DoctorServiceRepository serviceRepository;
    private final UserRepository userRepository;

    @Override
    public DoctorService createService(NewServiceDTO newServiceDTO) {
        if (serviceRepository.existsByName(newServiceDTO.getName())) {
            throw new DuplicateException("Tên dịch vụ đã tồn tại");
        }
        User doctor = userRepository.findById(newServiceDTO.getDoctorId()).orElseThrow(
                () -> new NotFoundException("Doctor not found")
        );
        //Create a new service entity and map the DTO properties
        DoctorService newService = mapper.map(newServiceDTO, DoctorService.class);
        newService.setDoctor(doctor);
        newService.setPrice(Double.parseDouble(newServiceDTO.getPrice().replace(".", "")));
        //Save the service in the repository
        return serviceRepository.save(newService);
    }

    @Override
    public DoctorService getServiceById(Long id) {
        //return service with searched id
        return serviceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("DoctorService not found")
        );
    }

    @Override
    public Page<ServiceDTO> getAllServices(Pageable pageable) {
        return serviceRepository.findAll(pageable)
                .map(d -> {
                    ServiceDTO dto = mapper.map(d, ServiceDTO.class);
                    dto.setDoctorName(d.getDoctor().getFullName());
                    // Trả về descriptions có độ dài tối đa 50 kí tự + "...."
//                    dto.setDescription(d.getDescription().substring(0, Math.min(d.getDescription().length(), 50)));
                    return dto;
                });
    }

    @Override
    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findAllServiceDTOs();
    }


    @Override
    public DoctorService updateService(DoctorService service, UpdateServiceDTO updateServiceDTO) {
        String newService = updateServiceDTO.getName();
//        /*Check if the new service name is the same with the service wants to update
//         * and check if the new service is already exist in repository*/
        if (!newService.equals(service.getName()) && serviceRepository.existsByName(newService)) {
            throw new DuplicateException("Tên dịch vụ đã tồn tại");
        }
        User doctor = userRepository.findById(updateServiceDTO.getDoctorId()).orElseThrow(
                () -> new NotFoundException("Doctor not found")
        );
//        //Map the properties from the DTO to the existing service entity
        updateServiceDTO.setPrice(updateServiceDTO.getPrice().replace(".", ""));
        mapper.map(updateServiceDTO, service);
        service.setDoctor(doctor);
//        //Save the service in the repository
        return serviceRepository.save(service);
    }

    @Override
    public UpdateServiceDTO getUpdateServiceById(Long id) {
        DoctorService service = getServiceById(id);
        UpdateServiceDTO updateServiceDTO = mapper.map(service, UpdateServiceDTO.class);
        updateServiceDTO.setDoctorId(service.getDoctor().getId());
        return updateServiceDTO;
    }

//    @Override
//    public List<DoctorService> searchService(String keyword) {
//        return serviceRepository.searchService(keyword);
//    }
//
//    @Override
//    public Page<DoctorService> getALlServices(Integer pageNo) {
//        Pageable pageable = PageRequest.of(pageNo - 1, 10);
//        return serviceRepository.findAll(pageable);
//    }

    @Override
    public Page<ServiceDTO> searchService(String keyword, Pageable pageNo) {
//        List list = serviceRepository.searchService(keyword);
//        Pageable pageable  = PageRequest.of(pageNo-1, 10);
//        Integer start = (int) pageable.getOffset();
//        Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() :pageable.getOffset() + pageable.getPageSize());
//        list = list.subList(start, end);
//
//        return new PageImpl<>(list, pageable, serviceRepository.searchService(keyword).size());
        return serviceRepository.findAllByNameLike("%" + keyword + "%", pageNo)
                .map(d -> {
                    // Trả về descriptions có độ dài tối đa 50 kí tự + "...."
//                    d.setDescription(d.getDescription().substring(0, Math.min(d.getDescription().length(), 50)));
                    return mapper.map(d, ServiceDTO.class);
                });
    }

    @Override
    public Page<ServiceHomeDTO> getAllHomepageServices(Pageable page, String search) {
        return serviceRepository.findServiceHomeDTO(page, search);
    }

    @Override
    public List<DoctorServiceHomeDTO> getAllServiceHome() {
        return serviceRepository.findAllServiceHomeDTOs();
    }

    @Override
    public DoctorService getTop1ServiceByDoctorId(Long doctorId) {
        return serviceRepository.findTop1ByDoctorId(doctorId);
    }

    @Override
    public Page<DoctorService> getAllServicesPage(Pageable pageable) {
        return serviceRepository.findAll(pageable);
    }

    @Override
    public List<DoctorService> getAllServicesPage() {
        return serviceRepository.findAll();
    }
}
