package com.example.project.admin.article;

import com.example.project.admin.article.dto.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    @Query("""
              SELECT new com.example.project.admin.article.dto.CategoryDTO
              (
                c.id,
                c.name,
                c.description,
                c.activeStatus,
                COUNT(a)
              )
              FROM Category c
              LEFT JOIN Article a ON c.id = a.category.id
              WHERE a.status = 'PUBLISHED'
              GROUP BY c
            """)
    List<CategoryDTO> findAllCategoryHomeDTOs();

    @Query("""
              SELECT new com.example.project.admin.article.dto.CategoryDTO
              (
                c.id,
                c.name,
                c.description,
                c.activeStatus,
                COUNT(a)
              )
              FROM Category c
              LEFT JOIN Article a ON c.id = a.category.id
              GROUP BY c
            """)
    List<CategoryDTO> findAllCategoryDTOs();

    @Query("""
              SELECT new com.example.project.admin.article.dto.CategoryDTO
              (
                c.id,
                c.name,
                c.description,
                c.activeStatus,
                COUNT(a)
              )
              FROM Category c
              LEFT JOIN Article a ON c.id = a.category.id
              WHERE a.status = 'PUBLISHED'
              AND c.id = :id
              GROUP BY c
            """)
    Optional<CategoryDTO> findAllCategoryDTOById(Long id);
}
