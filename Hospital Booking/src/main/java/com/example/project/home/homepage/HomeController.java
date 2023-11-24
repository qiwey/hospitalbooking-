package com.example.project.home.homepage;

import com.example.project.admin.article.ArticleService;
import com.example.project.admin.department.DepartmentService;
import com.example.project.admin.department.dto.DepartmentDTO;
import com.example.project.admin.homepage.HomePageInfo;
import com.example.project.admin.homepage.HomePageService;
import com.example.project.admin.staff.StaffService;
import com.example.project.admin.staff.dto.StaffDTO;
import com.example.project.home.article.ArticleHomeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HomePageService homePageService;
    private final DepartmentService departmentService;
    private final StaffService staffService;
    private final ArticleService articleService;

    @ModelAttribute("home")
    public HomePageInfo homePageInfo() {
        return homePageService.getHomePage();
    }

    @GetMapping({"/", "/trang-chu"})
    public String home(Model model) {
        List<ArticleHomeDTO> articles = articleService.getPinArticle();
        List<DepartmentDTO> departments = departmentService.getTop5Departments();
        List<StaffDTO> doctors = staffService.getTop4Doctor();
        model.addAttribute("articles", articles);
        model.addAttribute("departments", departments);
        model.addAttribute("doctors", doctors);
        return "homepage/index";
    }
}
