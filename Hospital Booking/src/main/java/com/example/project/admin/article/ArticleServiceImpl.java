package com.example.project.admin.article;

import com.example.project.admin.article.dto.*;
import com.example.project.admin.user.UserRepository;
import com.example.project.home.article.ArticleHomeDTO;
import com.example.project.base.constant.ArticleStatus;
import com.example.project.base.exception.DuplicateException;
import com.example.project.base.exception.NotFoundException;
import com.example.project.admin.user.User;
import com.example.project.util.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ModelMapper mapper;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final StorageService storageService;

//    @Override
//    public Article createArticle(User author, ArticleDTO newArticleDTO) {
//        if (articleRepository.existsByTitle(newArticleDTO.getTitle())) {
//            throw new DuplicateException("Tiêu đề đã tồn tại!");
//        }
//        Category category = categoryRepository.findById(newArticleDTO.getCategoryId()).orElseThrow(
//                () -> new NotFoundException("Chuyên mục không tồn tại!")
//        );
//        Article newArticle = mapper.map(newArticleDTO, Article.class);
//        newArticle.setCategory(category);
//        newArticle.setAuthor(author);
//        return articleRepository.save(newArticle);
//    }
//
//    @Override
//    public Article getArticleById(Long id) {
//        return articleRepository.findById(id).orElseThrow(
//                () -> new NotFoundException("News not found!")
//        );
//    }

    @Override
    @Transactional
    public Article createArticle(User user, NewArticleDTO newArticleDTO) {
        if (articleRepository.existsByTitle(newArticleDTO.getTitle())) {
            throw new DuplicateException("Tiêu đề đã tồn tại!");
        }
        Category category = categoryRepository.findById(newArticleDTO.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Chuyên mục không tồn tại!")
        );
        Article newArticle = mapper.map(newArticleDTO, Article.class);
        MultipartFile thumbnail = newArticleDTO.getThumbnailImage();
        if (thumbnail != null && !thumbnail.isEmpty()) {
            String thumbnailUrl = storageService.store(thumbnail);
            newArticle.setThumbnailUrl(thumbnailUrl);
        }
        newArticle.setCategory(category);
        User author = userRepository.findById(user.getId()).orElseThrow(
                () -> new NotFoundException("User not found!")
        );
        newArticle.setAuthor(author);
        return articleRepository.save(newArticle);
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElseThrow(
                () -> new NotFoundException("News not found!")
        );
    }

    @Override
    public List<Article> getAllArticle() {
        return articleRepository.findAll(Sort.by(Sort.Order.desc("lastModifiedAt")));
    }

    @Override
    public Page<ArticleDTO> getAllArticleDTO(Long categoryId, ArticleStatus status, Pageable pageable) {
        return articleRepository.findAllArticleDTO(categoryId, status, pageable);
    }

    @Override
    public List<ArticleDTO> getAllArticleDTO(Long categoryId, ArticleStatus status) {
        return articleRepository.findAllArticleDTO(categoryId, status);
    }

    @Override
    public Article updateArticle(Article article, UpdateArticleDTO updateArticleDTO) {
        Category category = categoryRepository.findById(updateArticleDTO.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Chuyên mục không tồn tại!")
        );
        mapper.map(updateArticleDTO, article);
        MultipartFile thumbnail = updateArticleDTO.getThumbnail();
        if (thumbnail != null && !thumbnail.isEmpty()) {
            String oldThumbnailUrl = article.getThumbnailUrl();
            String thumbnailUrl = storageService.store(thumbnail);
            article.setThumbnailUrl(thumbnailUrl);
            storageService.delete(oldThumbnailUrl);
        }
        article.setCategory(category);
        return articleRepository.save(article);
    }

//    @Override
//    public Article updateArticle(Article article, UpdateArticleDTO updateArticleDTO) {
//        String newTitle = updateArticleDTO.getTitle();
//        //String newContent = updateNewsDTO.getContent();
//
//        if (!newTitle.equals(article.getTitle()) && articleRepository.existsByTitle(newTitle)) {
//            throw new DuplicateException("Title is exits!");
//        }
//        mapper.map(updateArticleDTO, article);
//        return articleRepository.save(article);
//    }

    @Override
    public Category createCategory(NewCategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new DuplicateException("Tên chuyên mục đã tồn tại!");
        }
        Category category = mapper.map(categoryDTO, Category.class);
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category not found!")
        );
    }

    @Override
    public Category updateCategory(Category category, UpdateCategoryDTO updateCategoryDTO) {
        String newName = updateCategoryDTO.getName();

        if (!newName.equals(category.getName()) && categoryRepository.existsByName(newName)) {
            throw new DuplicateException("Tên chuyên mục đã tồn tại!");
        }
        mapper.map(updateCategoryDTO, category);
        return categoryRepository.save(category);
    }

    @Override
    public UpdateCategoryDTO getUpdateCategoryById(Long id) {
        Category category = getCategoryById(id);
        return mapper.map(category, UpdateCategoryDTO.class);
    }

    @Override
    public UpdateArticleDTO getUpdateArticleById(Long id) {
        Article article = getArticleById(id);
        UpdateArticleDTO updateArticleDTO = mapper.map(article, UpdateArticleDTO.class);
        updateArticleDTO.setCategoryId(article.getCategory().getId());
        return updateArticleDTO;
    }

    @Override
    public List<ArticleHomeDTO> getAllArticle(Long categoryId) {
        return articleRepository.findAllArticle(categoryId);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAllCategoryHomeDTOs();
    }

    @Override
    public CategoryDTO getCategoryDTOById(Long categoryId) {
        return categoryRepository.findAllCategoryDTOById(categoryId).orElse(null);
    }

    @Override
    public List<ArticleHomeDTO> getPopularArticle() {
        return articleRepository.findTop3ViewCountArticle();
    }

    @Override
    public List<ArticleHomeDTO> getAllArticleByCategory(Category category, Article article) {
        return articleRepository.find3Article(category.getId(), article.getId());
    }

    @Override
    public Page<ArticleHomeDTO> getAllArticle(Long categoryId, Pageable pageable) {
        return articleRepository.findAllArticle(categoryId, pageable);
    }

    @Override
    public List<CategoryDTO> getAllCategoryDTOs() {
        return categoryRepository.findAllCategoryDTOs();
    }

    @Override
    public List<ArticleHomeDTO> getPinArticle() {
        return articleRepository.findAllPinArticle();
    }

    @Override
    public void increaseView(Article article) {
        article.setViewCount(article.getViewCount() + 1);
        articleRepository.save(article);
    }

    @Override
    public void updateArticlePinStatus(Article article, Boolean status) {
        article.setPinStatus(status);
        articleRepository.save(article);
    }

    @Override
    public List<Comment> getAllCommentByArticle(Article article) {
        return commentRepository.findAllByArticle(article);
    }

    @Override
    public Page<Comment> findCommentByArticle(Integer pageNo, Article article) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("createdAt").descending());
        return this.commentRepository.findCommentByArticle(pageable, article);
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findCommentByArticleId(Long id) {
        return commentRepository.findAllByArticleId(id, Sort.by(Sort.Order.desc("createdAt")));
    }
}