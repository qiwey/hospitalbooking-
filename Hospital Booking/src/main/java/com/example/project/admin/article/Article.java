package com.example.project.admin.article;

import com.example.project.base.constant.ArticleStatus;
import com.example.project.admin.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    @Fetch(value = FetchMode.JOIN)
    private User author;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @Fetch(value = FetchMode.JOIN)
    private Category category;

    @Column(name = "title")
    private String title;

    @Column(name = "home_pin")
    private Boolean pinStatus;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "content", columnDefinition = "MEDIUMTEXT")
    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus status = ArticleStatus.DRAFT;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        lastModifiedAt = LocalDateTime.now();
        viewCount = 0;
        pinStatus = false;
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedAt = LocalDateTime.now();
    }
}
