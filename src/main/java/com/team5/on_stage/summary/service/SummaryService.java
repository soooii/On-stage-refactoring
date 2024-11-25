package com.team5.on_stage.summary.service;

import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.article.repository.ArticleRepository;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;
import com.team5.on_stage.summary.entity.Summary;
import com.team5.on_stage.summary.mapper.SummaryMapper;
import com.team5.on_stage.summary.repository.SummaryRespository;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SummaryService {
    private final ArticleRepository articleRepository;
    private final SummaryRespository summaryRespository;
    private final SummaryMapper summaryMapper;
    private final ChatGPTService chatGPTService;
    private final UserRepository userRepository;

    //해당 userId의 summary 저장
    public void saveSummary(Long userId) {
        List<Article> articles = articleRepository.findAllByUserId(userId);
        User user = userRepository.findById(userId).get();
        String allArticles = articles.stream()
                .map(article -> article.getContent())
                .collect(Collectors.joining());

        String prompt = """
                다음 문장을 부정적이거나 논란이 될 수 있는 주제는 배제하고,
                아티스트를 소개할 수 있는 측면에 초점을 맞춰서 두 개의 간결한 뉴스 기사로 요약해줘 : %s
                """.formatted(allArticles);

        String summarizedNews = chatGPTService.sendMessage(prompt);
        Summary summary = Summary.builder()
                .user(user)
                .summary(summarizedNews)
                .build();
        summaryRespository.save(summary);
    }

    //해당 userId의 뉴스 요약 가져오기
    public List<SummaryResponseDTO> getSummary(Long userId) {
        List<Summary> summaries = summaryRespository.findSummariesByUserId(userId);
        List<SummaryResponseDTO> summaryList = new ArrayList<>();
        for (Summary summary : summaries){
            SummaryResponseDTO dto = summaryMapper.toDTO(summary);
            summaryList.add(dto);
        }
        return summaryList;
    }

    //해당 userId의 summary 삭제
    public void deleteSummary(Long userId) {
        summaryRespository.deleteSummariesByUserId(userId);
    }


}
