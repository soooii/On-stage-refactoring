package com.team5.on_stage.article.controller;

import com.team5.on_stage.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article/")
public class ArticleApiController {
    private final ArticleService articleService;

    // 기사 저장
    @PostMapping("/{username}")
    public ResponseEntity<String> saveArticle(@PathVariable String username) {
        articleService.save(username);
        return ResponseEntity.ok("저장이 완료되었습니다.");
    }

    // 기사 모두 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteArticle(@PathVariable String username) {
        articleService.delete(username);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}

