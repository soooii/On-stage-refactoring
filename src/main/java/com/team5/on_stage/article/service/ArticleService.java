package com.team5.on_stage.article.service;

import com.team5.on_stage.article.ArticleCrawlService;
import com.team5.on_stage.article.dto.ArticleRequestDTO;
import com.team5.on_stage.article.dto.ArticleResponseDTO;
import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.article.mapper.ArticleMapper;
import com.team5.on_stage.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    //private final ArticleMapper articleMapper;
    private final ArticleCrawlService articleCrawlService;
    //기사 저장
    public void save(String keyword) {
        List<ArticleRequestDTO> crawledArticles = articleCrawlService.crawlArticles(keyword);
        for (ArticleRequestDTO dto : crawledArticles) {
            //Article article = articleMapper.toEntity(dto);
            //articleRepository.save(article);
        }
    }

    //기사 모두 삭제
    public void delete() {
        articleRepository.deleteAll();
    }
}