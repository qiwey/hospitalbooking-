package com.example.project.admin.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByArticle(Article article);

    Page<Comment> findCommentByArticle(Pageable page, Article article);

    List<Comment> findAllByArticleId(Long id, Sort createdAt);
}
