package com.example.project.admin.article.dto;

import com.example.project.base.constant.ArticleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateArticleDTO {
    private Long id;
    private Long categoryId;
    private String title;
    private String description;
    private String content;
    private String thumbnailUrl;
    private MultipartFile thumbnail;
    private ArticleStatus status;
}
