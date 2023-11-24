package com.example.project.admin.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    Long id;
    String name;
    String description;
    Boolean activeStatus;
    Long numberOfArticles;
}
