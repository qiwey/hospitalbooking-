package com.example.project.admin.staff;

import com.example.project.admin.department.DepartmentService;
import com.example.project.util.excel.ExcelService;
import com.example.project.base.exception.DuplicateException;
import com.example.project.base.exception.FileException;
import com.example.project.base.exception.NotFoundException;
import com.example.project.base.exception.StorageException;
import com.example.project.admin.schedule.DoctorScheduleService;
import com.example.project.util.search.SearchService;
import com.example.project.admin.staff.dto.NewStaffDTO;
import com.example.project.admin.staff.dto.UpdateStaffDTO;
import com.example.project.admin.user.User;
import com.example.project.admin.staff.dto.StaffDTO;
import com.example.project.admin.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class StaffController {

    private final StaffService staffService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final SearchService searchService;
    private final ExcelService excelService;
    private final DoctorScheduleService doctorScheduleService;

    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        return authentication != null ? (User) authentication.getPrincipal() : null;
    }

    @GetMapping("/staffs")
    public String getAllStaff(
            Model model,
            @RequestParam(required = false, name = "search") String search,
            @RequestParam(required = false, name = "roleId") String roleId,
            @RequestParam(required = false, name = "departmentId") Long departmentId,
            @PageableDefault(
                    sort = "lastModifiedAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("roles", userService.getAllRoles());
        if ("ASSIS".equals(user.getRole().getId())) {
            departmentId = user.getDepartment().getId();
            roleId = "DOC";
        }
        List<StaffDTO> filtered = staffService.getAllStaffs(roleId, departmentId, pageable.getSort());
        Page<StaffDTO> page = searchService.search(search, filtered, pageable);
        model.addAttribute("page", page);
        return "dashboard/staff/staffs";
    }

    @GetMapping("/excel/export/staffs")
    public ResponseEntity<byte[]> exportExcel() throws IOException, IllegalAccessException {
        List<StaffDTO> list = staffService.getAllStaffs();
        byte[] result = excelService.exportXlsx(list, StaffDTO.class);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=staffs.xlsx")
                .body(result);
    }

    @GetMapping("/staffs/add")
    public String addStaff(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("newStaffDTO", new NewStaffDTO());
        return "dashboard/staff/add-staff";
    }

    @PostMapping("/staffs/add")
    public String addStaff(@Valid NewStaffDTO newStaffDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newStaffDTO", newStaffDTO);
            model.addAttribute("roles", userService.getAllRoles());
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "dashboard/staff/add-staff";
        }
        try {
            staffService.createStaff(newStaffDTO);
        } catch (DuplicateException e) {
            if (e.getMessage().contains("Email")) {
                result.rejectValue("email", "error.email", e.getMessage());
            } else {
                result.rejectValue("contactNumber", "error.contactNumber", e.getMessage());
            }
        } catch (NotFoundException e) {
            if (e.getMessage().contains("Vai trò")) {
                result.rejectValue("roleId", "error.roleId", e.getMessage());
            } else {
                result.rejectValue("departmentId", "error.departmentId", e.getMessage());
            }
        } catch (FileException | StorageException e) {
            result.rejectValue("picture", "error.picture", e.getMessage());
        }
        if (result.hasErrors()) {
            model.addAttribute("newStaffDTO", newStaffDTO);
            model.addAttribute("roles", userService.getAllRoles());
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "dashboard/staff/add-staff";
        }
        return "redirect:/dashboard/staffs";
    }

    @GetMapping("/staffs/edit/{id}")
    public String editStaff(@PathVariable Long id, Model model) {
        try {
            UpdateStaffDTO updateStaffDTO = staffService.getUpdateStaffById(id);
            model.addAttribute("updateStaffDTO", updateStaffDTO);
            model.addAttribute("roles", userService.getAllRoles());
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "dashboard/staff/edit-staff";
        } catch (NotFoundException e) {
            return "redirect:/dashboard/staffs";
        }
    }

    @PostMapping("/staffs/edit/{id}")
    public String editStaff(@PathVariable Long id, @Valid UpdateStaffDTO updateStaffDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updateStaffDTO", updateStaffDTO);
            model.addAttribute("roles", userService.getAllRoles());
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "dashboard/staff/edit-staff";
        }
        try {
            User user = staffService.getStaffById(id);
            staffService.updateStaff(user, updateStaffDTO);
        } catch (DuplicateException e) {
            if (e.getMessage().contains("Email")) {
                result.rejectValue("email", "error.email", e.getMessage());
            } else {
                result.rejectValue("contactNumber", "error.contactNumber", e.getMessage());
            }
        } catch (NotFoundException e) {
            if (e.getMessage().contains("Vai trò")) {
                result.rejectValue("roleId", "error.roleId", e.getMessage());
            } else {
                result.rejectValue("departmentId", "error.departmentId", e.getMessage());
            }
        } catch (FileException | StorageException e) {
            result.rejectValue("picture", "error.picture", e.getMessage());
        }
        if (result.hasErrors()) {
            model.addAttribute("updateStaffDTO", updateStaffDTO);
            model.addAttribute("roles", userService.getAllRoles());
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "dashboard/staff/edit-staff";
        }
        return "redirect:/dashboard/staffs";
    }

    @GetMapping("/staff/{id}")
    public String getStaffById(
            @PathVariable Long id,
            @RequestParam(name = "week", required = false) LocalDate firstDayOfWeek,
            Model model) {
        User user = staffService.getStaffById(id);
        LocalDate currentWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        if (firstDayOfWeek == null) {
            firstDayOfWeek = currentWeek;
            model.addAttribute("updateButton", true);
        } else if (firstDayOfWeek.isAfter(currentWeek)) {
            model.addAttribute("updateButton", true);
        }
        if ("DOC".equals(user.getRole().getId())) {
            List<String> times = doctorScheduleService.getSchedule(user, firstDayOfWeek);
            model.addAttribute("times", times);
        }
        model.addAttribute("staff", user);
        return "dashboard/staff/profile";
    }

    @PostMapping("/staff/{id}/schedule")
    public String schedule(
            @PathVariable Long id,
            @RequestParam(name = "week") LocalDate firstDayOfWeek,
            @RequestParam List<String> time,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        if (time == null) {
            time = new ArrayList<>();
        }
        try {
            doctorScheduleService.saveSchedule(id, time, firstDayOfWeek);
        } catch (IllegalStateException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
        }
        String referer = request.getHeader("Referer");
        if (referer.indexOf("error") > 0) {
            referer = referer.substring(0, referer.indexOf("error") - 1);
        }
        return "redirect:" + referer;
    }
}
