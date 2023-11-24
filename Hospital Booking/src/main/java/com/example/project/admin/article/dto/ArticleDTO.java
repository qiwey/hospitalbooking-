package com.example.project.admin.article.dto;

import com.example.project.base.constant.ArticleStatus;
import com.example.project.util.search.Searchable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ArticleDTO implements Searchable {
    private Long id;
    private String title;
    private String authorName;
    private String categoryName;
    private ArticleStatus status;
    private Boolean pinStatus;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Override
    public String getSearchableString() {
        return title + " " + authorName + " " + categoryName;
    }
}
