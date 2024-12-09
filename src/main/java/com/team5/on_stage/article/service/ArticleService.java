package com.team5.on_stage.article.service;

import com.team5.on_stage.article.dto.ArticleRequestDTO;
import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.article.mapper.ArticleMapper;
import com.team5.on_stage.article.repository.ArticleRepository;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ArticleCrawlService articleCrawlService;
    private final UserRepository userRepository;

    //해당 username의 기사 저장
    public void save(String username){

        //기존 article soft delete
        articleRepository.softDeleteByUsername(username);

        User user = userRepository.findByUsername(username);
        String keyword = user.getNickname();
        List<ArticleRequestDTO> crawledArticles = articleCrawlService.crawlArticles(keyword);

        //bulk insert
        List<Article> articles = crawledArticles.stream()
                .map(dto -> articleMapper.toEntityByUser(dto, user))
                .collect(Collectors.toList());

        articleRepository.saveAll(articles);
    }

    //해당 username의 기사 모두 삭제
    public void delete(String username) {
        articleRepository.softDeleteByUsername(username);
    }
}