package com.example.project.admin.article;

import com.example.project.admin.article.dto.ArticleDTO;
import com.example.project.home.article.ArticleHomeDTO;
import com.example.project.base.constant.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByTitle(String title);

    @Query(value = """
            SELECT new com.example.project.admin.article.dto.ArticleDTO
            (
                u.id,
                u.title,
                CONCAT(u.author.lastName, ' ', u.author.firstName),
                u.category.name,
                u.status,
                u.pinStatus,
                u.createdAt,
                u.lastModifiedAt
            )
            FROM Article u
            WHERE (:categoryId IS NULL OR u.category.id = :categoryId)
            AND (:status IS NULL OR u.status = :status)
            """
    )
    Page<ArticleDTO> findAllArticleDTO(Long categoryId, ArticleStatus status, Pageable pageable);

    @Query(value = """
            SELECT new com.example.project.admin.article.dto.ArticleDTO
            (
                u.id,
                u.title,
                CONCAT(u.author.lastName, ' ', u.author.firstName),
                u.category.name,
                u.status,
                u.pinStatus,
                u.createdAt,
                u.lastModifiedAt
            )
            FROM Article u
            WHERE (:categoryId IS NULL OR u.category.id = :categoryId)
            AND (:status IS NULL OR u.status = :status)
            """
    )
    List<ArticleDTO> findAllArticleDTO(Long categoryId, ArticleStatus status);

    @Query(value = """
            SELECT new com.example.project.home.article.ArticleHomeDTO
            (
                u.id,
                CONCAT(u.author.lastName, ' ', u.author.firstName),
                u.category.name,
                u.title,
                u.viewCount,
                u.description,
                u.createdAt,
                u.thumbnailUrl
            )
            FROM Article u
            WHERE u.status = 'PUBLISHED'
            AND (:categoryId IS NULL OR u.category.id = :categoryId)
            """
    )
    List<ArticleHomeDTO> findAllArticle(Long categoryId);

    @Query(value = """
            SELECT new com.example.project.home.article.ArticleHomeDTO
            (
                u.id,
                CONCAT(u.author.lastName, ' ', u.author.firstName),
                u.category.name,
                u.title,
                u.viewCount,
                u.description,
                u.createdAt,
                u.thumbnailUrl
            )
            FROM Article u
            WHERE u.status = 'PUBLISHED'
            """
    )
    List<ArticleHomeDTO> findAllArticle();

    @Query(value = """
            SELECT new com.example.project.home.article.ArticleHomeDTO
            (
                u.id,
                CONCAT(u.author.lastName, ' ', u.author.firstName),
                u.category.name,
                u.title,
                u.viewCount,
                u.description,
                u.createdAt,
                u.thumbnailUrl
            )
            FROM Article u
            WHERE u.status = 'PUBLISHED'
            ORDER BY u.viewCount DESC
            LIMIT 3
            """
    )
    List<ArticleHomeDTO> findTop3ViewCountArticle();

    @Query(value = """
            SELECT new com.example.project.home.article.ArticleHomeDTO
            (
                u.id,
                CONCAT(u.author.lastName, ' ', u.author.firstName),
                u.category.name,
                u.title,
                u.viewCount,
                u.description,
                u.createdAt,
                u.thumbnailUrl
            )
            FROM Article u
            WHERE u.status = 'PUBLISHED'
            AND (:categoryId IS NULL OR u.category.id = :categoryId)
            AND (u.id != :articleId)
            ORDER BY u.createdAt DESC
            LIMIT 3
            """
    )
    List<ArticleHomeDTO> find3Article(Long categoryId, Long articleId);

    @Query(value = """
            SELECT new com.example.project.home.article.ArticleHomeDTO
            (
                u.id,
                CONCAT(u.author.lastName, ' ', u.author.firstName),
                u.category.name,
                u.title,
                u.viewCount,
                u.description,
                u.createdAt,
                u.thumbnailUrl
            )
            FROM Article u
            WHERE u.status = 'PUBLISHED'
            AND (:categoryId IS NULL OR u.category.id = :categoryId)
            """
    )
    Page<ArticleHomeDTO> findAllArticle(Long categoryId, Pageable pageable);

    @Query(value = """
            SELECT new com.example.project.home.article.ArticleHomeDTO
            (
                u.id,
                CONCAT(u.author.lastName, ' ', u.author.firstName),
                u.category.name,
                u.title,
                u.viewCount,
                u.description,
                u.createdAt,
                u.thumbnailUrl
            )
            FROM Article u
            WHERE u.status = 'PUBLISHED'
            AND u.pinStatus = TRUE
            """
    )
    List<ArticleHomeDTO> findAllPinArticle();
}
