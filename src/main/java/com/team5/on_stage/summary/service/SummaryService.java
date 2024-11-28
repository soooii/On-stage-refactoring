package com.team5.on_stage.summary.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.article.repository.ArticleRepository;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;
import com.team5.on_stage.summary.entity.QSummary;
import com.team5.on_stage.summary.entity.Summary;
import com.team5.on_stage.summary.mapper.SummaryMapper;
import com.team5.on_stage.summary.repository.SummaryRespository;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    private final JPAQueryFactory jpaQueryFactory;

    //해당 userId의 summary 저장
    public void saveSummary(Long userId) {
        List<Article> articles = articleRepository.findAllByUserId(userId);
        User user = userRepository.findById(userId).get();
        String allArticles = articles.stream()
                .map(article -> article.getContent())
                .collect(Collectors.joining());

        //기존 Summary soft delete
        summaryRespository.deleteSummariesByUserId(userId);

        String prompt = """
                다음 문장을 부정적이거나 논란이 될 수 있는 주제는 배제하고,
                아티스트를 소개할 수 있는 측면에 초점을 맞춰서 4개의 간결한 뉴스 기사로 요약해줘 : %s
                """.formatted(allArticles);

        String summarizedNews = chatGPTService.sendMessage(prompt);
        String[] separatedSummaries = summarizedNews.split("\n");

        List<Summary> summaries = new ArrayList<>();
        for (String summaryText : separatedSummaries) {
            Summary summary = Summary.builder()
                    .user(user)
                    .summary(summaryText)
                    .build();
            summaries.add(summary);
        }

        summaryRespository.saveAll(summaries); //bulk insert
    }

    // saveSummary 호출하는 순간부터 3개월마다 자동으로 saveSummary 동작되도록 -> taskScheduler?
    // 해당 userId의 뉴스 요약 가져오기 (페이지네이션 적용)
    public Page<SummaryResponseDTO> getSummary(Long userId, int page, int size) {
        QSummary summary = QSummary.summary1;
        BooleanExpression condition = summary.user.id.eq(userId); // userId로 필터링
        Pageable pageable = PageRequest.of(page, size);

        // querydsl
        List<Summary> summaries = jpaQueryFactory
                .selectFrom(summary)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //저장된 뉴스가 없을 경우
        if(summaries.isEmpty()){
            saveSummary(userId);
            getSummary(userId, page, size); //다시 요청
        }

        List<SummaryResponseDTO> summaryList = summaries.stream()
                .map(s->summaryMapper.toDTO(s))
                .collect(Collectors.toList());

        return new PageImpl<>(summaryList, pageable, summaries.size());
    }

    //해당 userId의 summary 삭제
    public void deleteSummary(Long userId) {
        summaryRespository.deleteSummariesByUserId(userId);
    }




}
