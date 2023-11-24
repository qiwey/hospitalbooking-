package com.example.project.admin.service;

import com.example.project.base.exception.DuplicateException;
import com.example.project.admin.service.dto.NewServiceDTO;
import com.example.project.admin.service.dto.ServiceDTO;
import com.example.project.admin.service.dto.UpdateServiceDTO;
import com.example.project.admin.staff.StaffService;
import com.example.project.admin.user.User;
import com.example.project.util.search.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class ServiceController {

    private final ServiceService serviceService;
    private final StaffService staffService;
    private final SearchService searchService;


    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            user.setPassword(null);
            return user;
        }
        return null;
    }

    @GetMapping("/services")
    public String getServices(
            Model model,
            @PageableDefault(sort = "lastModifiedAt", direction = Sort.Direction.DESC) Pageable pageable,
            @Param("search") String search) {
        List<ServiceDTO> list = serviceService.getAllServices();
        Page<ServiceDTO> page = searchService.search(search, list, pageable);
        model.addAttribute("page", page);
        return "dashboard/service/services";
    }

    @GetMapping("/add-service")
    public String addService(Model model) {
        model.addAttribute("newServiceDTO", new NewServiceDTO());
        model.addAttribute("doctors", staffService.getAllDoctor());
        return "dashboard/service/add-service";
    }

    @PostMapping("/add-service")
    public String addService(@Valid NewServiceDTO newServiceDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newServiceDTO", newServiceDTO);
            model.addAttribute("doctors", staffService.getAllDoctor());
            return "dashboard/service/add-service";
        }
        try {
            serviceService.createService(newServiceDTO);
        } catch (DuplicateException e) {
            bindingResult.rejectValue("name", "error.name", e.getMessage());
            model.addAttribute("doctors", staffService.getAllDoctor());
            model.addAttribute("newServiceDTO", newServiceDTO);
            return "dashboard/service/add-service";
        }
        return "redirect:/dashboard/services";
    }

    @GetMapping("/edit-service/{id}")
    public String editService(@PathVariable Long id, Model model) {
        UpdateServiceDTO updateServiceDTO = serviceService.getUpdateServiceById(id);
        model.addAttribute("updateServiceDTO", updateServiceDTO);
        model.addAttribute("doctors", staffService.getAllDoctor());
        return "dashboard/service/edit-service";
    }

    @PostMapping("/edit-service/{id}")
    public String editService(@PathVariable Long id, @Valid UpdateServiceDTO updateServiceDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("updateServiceDTO", updateServiceDTO);
            model.addAttribute("doctors", staffService.getAllDoctor());
            return "dashboard/service/edit-service";
        }
        try {
            DoctorService service = serviceService.getServiceById(updateServiceDTO.getId());
            serviceService.updateService(service, updateServiceDTO);
        } catch (DuplicateException e) {
            bindingResult.rejectValue("name", "error.name", e.getMessage());
            model.addAttribute("doctors", staffService.getAllDoctor());
            model.addAttribute("updateServiceDTO", updateServiceDTO);
            return "dashboard/service/edit-service";
        }
        return "redirect:/dashboard/services";
    }
}
