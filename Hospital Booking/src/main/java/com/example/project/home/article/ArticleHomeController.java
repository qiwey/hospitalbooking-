package com.example.project.home.article;

import com.example.project.admin.article.Article;
import com.example.project.admin.article.ArticleService;
import com.example.project.admin.article.Comment;
import com.example.project.admin.article.CommentRepository;
import com.example.project.admin.article.dto.CategoryDTO;
import com.example.project.admin.department.dto.NewDepartmentDTO;
import com.example.project.admin.homepage.HomePageInfo;
import com.example.project.admin.homepage.HomePageService;
import com.example.project.util.search.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleHomeController {

    private final ArticleService articleService;
    private final HomePageService homePageService;
    private final SearchService searchService;
    public Article articleGet = new Article();
    public Long idGet;

    @ModelAttribute("home")
    public HomePageInfo homePageInfo() {
        return homePageService.getHomePage();
    }

    @ModelAttribute("popular")
    public List<ArticleHomeDTO> getPopular() {
        return articleService.getPopularArticle();
    }

    @ModelAttribute("categories")
    public List<CategoryDTO> getCategories() {
        return articleService.getAllCategories();
    }

    @GetMapping("/tin-tuc")
    public String articleHomepage(
            Model model,
            @RequestParam(name = "category", required = false) Long categoryId,
            @RequestParam(name = "search", required = false) String search,
            @PageableDefault(
                    sort = "createdAt",
                    size = 7,
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        Page<ArticleHomeDTO> page;
        if (search != null && !search.isEmpty()) {
            List<ArticleHomeDTO> list = articleService.getAllArticle(categoryId);
            page = searchService.search(search, list, pageable);
        } else {
            page = articleService.getAllArticle(categoryId, pageable);
        }
        model.addAttribute("page", page);
        model.addAttribute("currentCategory", articleService.getCategoryDTOById(categoryId));
        return "homepage/blog";
    }

//    @GetMapping("/tin-tuc/{id}")
//    public String articleHomepage(
//            @PathVariable Long id,
//            Model model,
//            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo
//    ) {
//        Article article = articleService.getArticleById(id);
//        articleService.increaseView(article);
//        List<ArticleHomeDTO> relates = articleService.getAllArticleByCategory(article.getCategory(), article);
//        articleGet = article;
//        Page<Comment> comments = articleService.findCommentByArticle(pageNo, article);
//        model.addAttribute("relates", relates);
//        model.addAttribute("comments", comments);
//        model.addAttribute("article", article);
//        model.addAttribute("addcomments", new Comment());
//        return "homepage/blog-single";
//    }

    @GetMapping("/tin-tuc/{id}")
    public String articleHomepage(
            @PathVariable Long id,
            Model model,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo
    ) {
        Article article = articleService.getArticleById(id);
        articleService.increaseView(article);
        List<ArticleHomeDTO> relates = articleService.getAllArticleByCategory(article.getCategory(), article);
        articleGet = article;
        int tolalComment = articleService.getAllCommentByArticle(article).size();
        Page<Comment> comments = articleService.findCommentByArticle(pageNo, article);
        log.info(String.valueOf(comments.stream().toList().size()));
        //lấy tổng số page
        model.addAttribute("totalPageComment", comments.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("relates", relates);
        model.addAttribute("comments", comments);
        //đẩy số lượng comment sang view
        model.addAttribute("checkZeroCommentsize", comments.stream().toList().size());
        model.addAttribute("tolalComment", tolalComment);
        model.addAttribute("article", article);
        model.addAttribute("addcomments", new Comment());
        return "homepage/blog-single";
    }

    @PostMapping("/add-comment")
    public String CommentArticle(
            @Valid Comment comment,
            Model model
    ) {
        comment.setArticle(articleGet);
        articleService.addComment(comment);
        log.info("save thành công");
        return "redirect:/tin-tuc/" + articleGet.getId() + "#comment";
    }

    @GetMapping("remove-comment/{id}")
    public String RemoveComment(
            @PathVariable Long id,
            Model model
    ) {
        articleService.deleteComment(id);
        return "redirect:/tin-tuc/" + articleGet.getId() + "#comment";
    }

}
