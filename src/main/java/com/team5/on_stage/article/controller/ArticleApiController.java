package com.team5.on_stage.article.controller;


import com.team5.on_stage.article.dto.ArticleResponseDTO;
import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.article.entity.ArticleStatus;
import com.team5.on_stage.article.repository.ArticleRepository;
import com.team5.on_stage.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article/")
public class ArticleApiController {
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    // Todo - Patch로 수정
    @DeleteMapping
    public ResponseEntity<String> deleteArticle(Article article) {
        articleService.filteredArticleDelete(article);

        return ResponseEntity.ok().build();
    }

    // 해당 유저의 모든 기사 목록 조회
    @GetMapping
    public ResponseEntity<List<ArticleResponseDTO>> getAllArticles(String username) {
        List<ArticleResponseDTO> articles = articleService.getArticles(username);
        return ResponseEntity.ok(articles);
    }

    // 검수 승인
    // Todo - PathVariable에 articleId 넣어 식별 고민
    @PatchMapping("/approve")
    public ResponseEntity<String> approveArticle(ArticleResponseDTO articleResponseDTO) {
        Optional<Article> article = articleRepository.findById(articleResponseDTO.getId());
        Article existArticle = article.get();
        articleRepository.save(existArticle.updateStatus(ArticleStatus.APPROVED));
        return ResponseEntity.ok().build();
    }

    // 검수 거절
    @PatchMapping("/reject")
    public ResponseEntity<String> rejectArticle(ArticleResponseDTO articleResponseDTO) {
        Optional<Article> article = articleRepository.findById(articleResponseDTO.getId());
        Article existArticle = article.get();
        articleRepository.save(existArticle.updateStatus(ArticleStatus.REJECTED));
        return ResponseEntity.ok().build();
    }


}