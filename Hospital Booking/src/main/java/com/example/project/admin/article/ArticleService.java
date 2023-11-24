package com.example.project.admin.article;

import com.example.project.admin.article.dto.*;
import com.example.project.home.article.ArticleHomeDTO;
import com.example.project.base.constant.ArticleStatus;
import com.example.project.admin.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    Article createArticle(User author, NewArticleDTO newArticleDTO);

    Article getArticleById(Long id);

    List<Article> getAllArticle();

    Page<ArticleDTO> getAllArticleDTO(Long categoryId, ArticleStatus status, Pageable pageable);

    List<ArticleDTO> getAllArticleDTO(Long categoryId, ArticleStatus status);

    Article updateArticle(Article article, UpdateArticleDTO updateArticleDTO);

    Category createCategory(NewCategoryDTO categoryDTO);

    Category getCategoryById(Long id);

//    List<Category> getAllCategories();

    Category updateCategory(Category category, UpdateCategoryDTO updateCategoryDTO);

    UpdateCategoryDTO getUpdateCategoryById(Long id);

    UpdateArticleDTO getUpdateArticleById(Long id);

    List<ArticleHomeDTO> getAllArticle(Long categoryId);

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryDTOById(Long categoryId);

    List<ArticleHomeDTO> getPopularArticle();

    List<ArticleHomeDTO> getAllArticleByCategory(Category category, Article article);

    Page<ArticleHomeDTO> getAllArticle(Long categoryId, Pageable pageable);

    List<CategoryDTO> getAllCategoryDTOs();

    List<ArticleHomeDTO> getPinArticle();

    void increaseView(Article article);

    void updateArticlePinStatus(Article article, Boolean status);

    List<Comment> getAllCommentByArticle(Article article);

    Page<Comment> findCommentByArticle(Integer pageNo, Article article);

    Comment addComment(Comment comment);

    void deleteComment(Long id);

    List<Comment> findCommentByArticleId(Long id);
}

