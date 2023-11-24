package com.example.project.admin.article.dto;

import com.example.project.base.constant.ArticleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewArticleDTO {
    @NotNull
    private MultipartFile thumbnailImage;
    @NotBlank
    private String description;
    @NotNull
    private Long categoryId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private ArticleStatus status;
}
