package com.team5.on_stage.article.service;

import com.team5.on_stage.article.dto.ArticleRequestDTO;
import com.team5.on_stage.article.dto.ArticleResponseDTO;
import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.article.mapper.ArticleMapper;
import com.team5.on_stage.article.repository.ArticleRepository;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import com.team5.on_stage.util.chatGPT.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final ChatGPTService chatGPTService;
    private final SimpMessagingTemplate brokerMessagingTemplate;

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

    // 해당 유저의 기사 목록 조회
    public List<ArticleResponseDTO> getArticles(String username) {

        return articleRepository.findAllByUser_Username(username).stream()
                .map(entity->articleMapper.toDto(entity))
                .collect(Collectors.toList());
    }


    // 1차 필터링
    public void firstFilteredArticles(String username) {
        List<Article> articles = articleRepository.findAllByUser_Username(username);

        User user = userRepository.findByUsername(username);
        String nickname = user.getNickname();

        for (Article article : articles) {
            String prompt = """
            아래 기사가 '%s'와 직접적으로 관련이 없거나,
            부정적이거나 논란이 될 수 있는 내용이라면 NO를 보내줘:
            %s
            """.formatted(nickname, article.getContent());

            String status = chatGPTService.sendMessage(prompt);

            if (status.equals("NO")) {
                articleRepository.delete(article);
            }
        }

        // 관리자 알림
        sendConfirmRequest(username);
    }

    // 해당 기사 삭제
    public void filteredArticleDelete(Article article) {articleRepository.delete(article);}

    //해당 username의 기사 모두 삭제
    public void delete(String username) {articleRepository.softDeleteByUsername(username);}

    // 관리자 검수 요청 알림
    // Todo - 관리자 계정 추가
    public void sendConfirmRequest(String username) {
        // User admin = userRepository.findByRole("ADMIN");  // 관리자 역할을 가진 사용자 가져오기
        // brokerMessagingTemplate.convertAndSendToUser(admin,"/queue/confirm",username+"으로부터 검수 요청이 도착했습니다.");
    }
}