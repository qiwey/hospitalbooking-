package com.example.project.admin.department;

import com.example.project.admin.department.dto.DepartmentDTO;
import com.example.project.admin.department.dto.NewDepartmentDTO;
import com.example.project.admin.department.dto.UpdateDepartmentDTO;
import com.example.project.admin.user.User;
import com.example.project.base.exception.DuplicateException;
import com.example.project.util.search.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class DepartmentController {
    private final DepartmentService departmentService;
    private final SearchService searchService;
    private final ModelMapper mapper;

    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            user.setPassword(null);
            return user;
        }
        return null;
    }

    @GetMapping("/departments")
    public String getAllDepartment(
            @PageableDefault(
                    sort = "lastModifiedAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable,
            Model model,
            @Param("search") String search) {
        List<DepartmentDTO> departments = departmentService.getAllDepartmentDTOs();
        Page<DepartmentDTO> page = searchService.search(search, departments, pageable);
        model.addAttribute("page", page);
        return "dashboard/department/departments";
    }

//    @GetMapping("/departments")
//    public String getDepartments(Model model, @Param("keyword") String keyword,
//                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
//                                 @RequestParam(name = "numberPage", defaultValue = "5") Integer numberPage) {
//        Page<Department> listDepartments = departmentService.getAllDepartments(pageNo, numberPage);
//
//        if (keyword != null) {
//            listDepartments = departmentService.searchDepartment(keyword, pageNo, numberPage);
//        }
//        //   trả về số lượng page sang html
//        model.addAttribute("totalPage", listDepartments.getTotalPages());
//        model.addAttribute("currentPage", pageNo);
//
//        // trả về keyword để khi click html k bị mất keyword
//        model.addAttribute("keyword", DepartmentServiceImpl.trimString(keyword));
//        model.addAttribute("numberPage", numberPage);
//        //trả về list department đc load ra
//        model.addAttribute("department", listDepartments);
//        return "dashboard/department/departments";
//    }

    @GetMapping("/add-department")
    public String getDepartment(Model model) {
        model.addAttribute("newDepartmentDTO", new NewDepartmentDTO());
        return "dashboard/department/add-department";
    }

    @PostMapping("/add-department")
    public String postAddDepartment(@Valid NewDepartmentDTO newDepartmentDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("newDepartmentDTO", newDepartmentDTO);
            return "dashboard/department/add-department";
        }
        try {
            departmentService.createDepartment(newDepartmentDTO);
        } catch (DuplicateException e) {
            bindingResult.rejectValue("departmentId", "error.departmentId", e.getMessage());
            model.addAttribute("newDepartmentDTO", newDepartmentDTO);
            return "dashboard/department/add-department";
        }
        return "redirect:/dashboard/departments";
    }

    @GetMapping("/edit-department/{id}")
    public String getEditDoctor(@PathVariable Long id, Model model) {
        UpdateDepartmentDTO updateDepartmentDTO = departmentService.getUpdateDepartmentById(id);
        model.addAttribute("updateDepartmentDTO", updateDepartmentDTO);
        return "dashboard/department/edit-department";
    }

    @PostMapping("edit-department/{id}")
    public String editDepart(
            @PathVariable Long id,
            @Valid UpdateDepartmentDTO updateDepartmentDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("updateDepartmentDTO", updateDepartmentDTO);
            return "dashboard/department/edit-department";
        }
        try {
            Department department = departmentService.getDepartmentById(id);
            departmentService.updateDepartment(department, updateDepartmentDTO);
        } catch (DuplicateException e) {
            bindingResult.rejectValue("code", "error.code", e.getMessage());
            model.addAttribute("updateDepartmentDTO", updateDepartmentDTO);
            return "dashboard/department/edit-department";
        }

        return "redirect:/dashboard/departments";
    }


}
