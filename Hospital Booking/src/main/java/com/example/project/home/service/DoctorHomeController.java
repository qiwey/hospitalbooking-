package com.example.project.home.service;

import com.example.project.admin.appointment.AppointmentService;
import com.example.project.admin.homepage.HomePageInfo;
import com.example.project.admin.homepage.HomePageService;
import com.example.project.admin.service.DoctorService;
import com.example.project.admin.service.ServiceService;
import com.example.project.admin.staff.StaffService;
import com.example.project.admin.staff.dto.StaffDTO;
import com.example.project.util.search.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DoctorHomeController {

    private final HomePageService homePageService;
    private final StaffService staffService;
    private final SearchService searchService;
    private final ServiceService serviceService;

    @ModelAttribute("home")
    public HomePageInfo homePageInfo() {
        return homePageService.getHomePage();
    }

    @GetMapping("/bac-si")
    public String getAllDoctors(
            Model model,
            @RequestParam(name = "search", required = false) String search,
            @PageableDefault(
                    sort = "createdAt",
                    size = 12,
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        if (search != null && !search.isBlank()) {
            List<StaffDTO> doctors = staffService.getAllDoctor();
            Page<StaffDTO> page = searchService.search(search, doctors, pageable);
            model.addAttribute("page", page);
            return "homepage/doctor-list";
        }
        Page<StaffDTO> page = staffService.getAllHomeDoctor(pageable);
        model.addAttribute("page", page);
        return "homepage/doctor-list";
    }

    @GetMapping("/dich-vu")
    public String getAllServices(
            Model model,
            @RequestParam(name = "search", required = false) String search,
            @PageableDefault(
                    sort = "createdAt",
                    size = 12,
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        if (search != null && !search.isBlank()) {
            List<DoctorService> services = serviceService.getAllServicesPage();
            Page<DoctorService> page = searchService.search(search, services, pageable);
            model.addAttribute("page", page);
            return "homepage/service-list";
        }
        Page<DoctorService> page = serviceService.getAllServicesPage(pageable);
        model.addAttribute("page", page);
        return "homepage/service-list";
    }

}
