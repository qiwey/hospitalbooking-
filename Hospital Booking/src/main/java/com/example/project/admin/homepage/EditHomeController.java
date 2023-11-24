package com.example.project.admin.homepage;

import com.example.project.admin.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EditHomeController {

    private final HomePageService homePageService;

    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        return authentication != null ? (User) authentication.getPrincipal() : null;
    }

    @GetMapping("/dashboard/edit-homepage")
    public String editHomePage(Model model) {
        HomePageInfo homePageInfo = homePageService.getHomePage();
        model.addAttribute("homePageInfo", homePageInfo);
        return "dashboard/homepage/edit-homepage";
    }

    @PostMapping("/dashboard/edit-homepage")
    public String editHomePage(
            HomePageInfo homePageInfo,
            @RequestParam MultipartFile logo,
            @RequestParam MultipartFile aboutImage) {
        log.warn("{}", logo.getOriginalFilename());
        homePageService.updateHomePage(homePageInfo, logo, aboutImage);
        return "redirect:/dashboard/edit-homepage";
    }
}
