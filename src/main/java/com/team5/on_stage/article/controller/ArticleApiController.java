package com.team5.on_stage.article.controller;

import com.team5.on_stage.article.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Article API", description = "사용자의 아티스트 뉴스 기사를 저장, 삭제하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/article/")
public class ArticleApiController {
    private final ArticleService articleService;

    @Operation(summary = "아티스트 뉴스 기사 저장", description = "특정 사용자의 아티스트 뉴스 기사 데이터를 저장합니다.")
    @Parameter(name = "username", description = "기사 데이터를 저장할 사용자의 username")
    @PostMapping("/{username}")
    public ResponseEntity<String> saveArticle(@PathVariable String username) {
        articleService.save(username);
        return ResponseEntity.ok("저장이 완료되었습니다.");
    }

    @Operation(summary = "아티스트 뉴스 기사 삭제", description = "특정 사용자의 아티스트 뉴스 기사 데이터를 삭제합니다.")
    @Parameter(name = "username", description = "기사 데이터를 저장할 사용자의 username")
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteArticle(@PathVariable String username) {
        articleService.delete(username);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}

