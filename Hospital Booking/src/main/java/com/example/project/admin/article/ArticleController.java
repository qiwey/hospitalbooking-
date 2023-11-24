package com.example.project.admin.article;

import com.example.project.base.constant.ArticleStatus;
import com.example.project.base.exception.DuplicateException;
import com.example.project.admin.article.dto.*;
import com.example.project.util.search.SearchService;
import com.example.project.admin.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
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

    @GetMapping("/categories")
    public String newsCategory(Model model) {
        model.addAttribute("categories", articleService.getAllCategoryDTOs());
        return "dashboard/article/categories";
    }

    @GetMapping("/add-category")
    public String addCategory(Model model) {
        model.addAttribute("newCategoryDTO", new NewCategoryDTO());
        return "dashboard/article/add-category";
    }

    @PostMapping("/add-category")
    public String addCategory(@Valid NewCategoryDTO newCategoryDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newCategoryDTO", newCategoryDTO);
            return "dashboard/article/add-category";
        }
        try {
            articleService.createCategory(newCategoryDTO);
        } catch (DuplicateException e) {
            bindingResult.rejectValue("name", "error.name", e.getMessage());
            model.addAttribute("newCategoryDTO", newCategoryDTO);
            return "dashboard/article/add-category";
        }
        return "redirect:/dashboard/categories";
    }

    @GetMapping("/edit-category/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        UpdateCategoryDTO updateCategoryDTO = articleService.getUpdateCategoryById(id);
        model.addAttribute("updateCategoryDTO", updateCategoryDTO);
        return "dashboard/article/edit-category";
    }

    @PostMapping("/edit-category/{id}")
    public String editCategory(@Valid UpdateCategoryDTO updateCategoryDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("updateCategoryDTO", updateCategoryDTO);
            return "dashboard/article/edit-category";
        }
        try {
            Category category = articleService.getCategoryById(updateCategoryDTO.getId());
            articleService.updateCategory(category, updateCategoryDTO);
        } catch (DuplicateException e) {
            bindingResult.rejectValue("name", "error.name", e.getMessage());
            model.addAttribute("updateCategoryDTO", updateCategoryDTO);
            return "dashboard/article/edit-category";
        }
        return "redirect:/dashboard/categories";
    }

    @GetMapping("/articles")
    public String news(
            @RequestParam(required = false, name = "search") String search,
            @RequestParam(required = false, name = "categoryId") Long categoryId,
            @RequestParam(required = false, name = "status") ArticleStatus status,
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable,
            Model model
    ) {
        model.addAttribute("categories", articleService.getAllCategoryDTOs());
        Page<ArticleDTO> page;
        if (search != null && !search.isEmpty()) {
            List<ArticleDTO> articles = articleService.getAllArticleDTO(categoryId, status);
            page = searchService.search(search, articles, pageable);
        } else {
            page = articleService.getAllArticleDTO(categoryId, status, pageable);
        }
        model.addAttribute("page", page);
        return "dashboard/article/articles";
    }

    @GetMapping("/add-article")
    public String addArticle(Model model) {
        model.addAttribute("newArticleDTO", new NewArticleDTO());
        model.addAttribute("categories",
                articleService.getAllCategoryDTOs()
                        .stream().filter(CategoryDTO::getActiveStatus)
                        .toList()
        );
        return "dashboard/article/add-article";
    }

    @PostMapping("/add-article")
    public String addArticle(@Valid NewArticleDTO newArticleDTO, Model model, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newArticleDTO", newArticleDTO);
            model.addAttribute("categories",
                    articleService.getAllCategoryDTOs()
                            .stream().filter(CategoryDTO::getActiveStatus)
                            .toList()
            );
            return "dashboard/article/add-article";
        }
        try {
            User author = (User) authentication.getPrincipal();
            articleService.createArticle(author, newArticleDTO);
        } catch (DuplicateException e) {
            bindingResult.rejectValue("title", "error.title", e.getMessage());
            model.addAttribute("newArticleDTO", newArticleDTO);
            model.addAttribute("categories",
                    articleService.getAllCategoryDTOs()
                            .stream().filter(CategoryDTO::getActiveStatus)
                            .toList()
            );
            return "dashboard/article/add-article";
        }
        return "redirect:/dashboard/articles";
    }

    @GetMapping("/edit-article/{id}")
    public String editArticle(@PathVariable Long id, Model model) {
        UpdateArticleDTO updateNewsDTO = articleService.getUpdateArticleById(id);
        model.addAttribute("updateArticleDTO", updateNewsDTO);
        model.addAttribute("categories",
                articleService.getAllCategoryDTOs()
                        .stream().filter(CategoryDTO::getActiveStatus)
                        .toList()
        );
        return "dashboard/article/edit-article";
    }

    @PostMapping("/edit-article/{id}")
    public String editArticle(
            @PathVariable Long id,
            @Valid UpdateArticleDTO updateArticleDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            log.warn("{}", bindingResult.getAllErrors());
            model.addAttribute("updateArticleDTO", updateArticleDTO);
            model.addAttribute("categories",
                    articleService.getAllCategoryDTOs()
                            .stream().filter(CategoryDTO::getActiveStatus)
                            .toList()
            );
            return "dashboard/article/edit-article";
        }
        Article article = articleService.getArticleById(id);
        articleService.updateArticle(article, updateArticleDTO);
        return "redirect:/dashboard/articles";
    }

    @PostMapping("article/{id}/update-status")
    public String updateArticleStatus(
            @PathVariable Long id,
            Boolean status,
            HttpServletRequest request
    ) {
        Article article = articleService.getArticleById(id);
        articleService.updateArticlePinStatus(article, status);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}