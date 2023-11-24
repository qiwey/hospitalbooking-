package com.example.project.home.article;

import com.example.project.util.search.Searchable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ArticleHomeDTO implements Searchable {
    private Long id;
    private String authorName;
    private String categoryName;
    private String title;
    private Integer viewCount;
    private String description;
    private LocalDateTime createAt;
    private String thumbnailUrl;

    @Override
    public String getSearchableString() {
        return title + " " + categoryName + " " + authorName;
    }
}
