package com.team5.on_stage.article.controller;

import com.team5.on_stage.article.dto.ArticleRequestDTO;
import com.team5.on_stage.article.dto.ArticleResponseDTO;
import com.team5.on_stage.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article/")
public class ArticleApiController {
    private final ArticleService articleService;

    //기사 저장
    @PostMapping
    public ResponseEntity<String> save(String keyword){
        articleService.save(keyword);
        return ResponseEntity.ok("저장이 완료되었습니다.");
    }

    //기사 모두 삭제
    @DeleteMapping
    public ResponseEntity<String> delete(){
        articleService.delete();
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

}
