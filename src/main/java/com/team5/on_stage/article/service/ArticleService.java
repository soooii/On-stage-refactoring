package com.team5.on_stage.article.service;

import com.team5.on_stage.article.dto.ArticleRequestDTO;
import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.article.mapper.ArticleMapper;
import com.team5.on_stage.article.repository.ArticleRepository;
import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ArticleCrawlService articleCrawlService;
    private final UserRepository userRepository;

    //해당 userId의 기사 저장
    public void save(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        String keyword = user.getNickname();
        List<ArticleRequestDTO> crawledArticles = articleCrawlService.crawlArticles(keyword);
        for (ArticleRequestDTO dto : crawledArticles) {
            Article article = articleMapper.toEntityByUser(dto,user);
            articleRepository.save(article);
        }
    }

    //해당 userId의 기사 모두 삭제
    public void delete(Long userId) {
        articleRepository.deleteAllByUserId(userId);
    }
}