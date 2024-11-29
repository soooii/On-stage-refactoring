package com.team5.on_stage.summary.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.article.repository.ArticleRepository;
import com.team5.on_stage.summary.dto.SummaryRequestDTO;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;

import com.team5.on_stage.summary.entity.QSummary;
import com.team5.on_stage.summary.entity.Summary;
import com.team5.on_stage.summary.mapper.SummaryMapper;
import com.team5.on_stage.summary.repository.SummaryRespository;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import com.team5.on_stage.util.chatGPT.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
        //articleService.saveArticle(userId);
        List<Article> articles = articleRepository.findAllByUserId(userId);
        User user = userRepository.findById(userId).get();
        String allArticles = articles.stream()
                .map(article -> article.getContent())
                .collect(Collectors.joining());

        //기존 Summary soft delete
        summaryRespository.softDeleteByUserId(userId);

        String prompt = """
                다음 문장을 부정적이거나 논란이 될 수 있는 주제는 배제하고,
                아티스트를 소개할 수 있는 측면에 초점을 맞춰서 기사 번호없이 4개의 요약을 제목과 4줄의 내용으로 작성해줘:%s
                """.formatted(allArticles);

        String summarizedNews = chatGPTService.sendMessage(prompt);

        log.info("ChatGPT Response: \n{}", summarizedNews);

        String[] separatedSummaries = summarizedNews.split("\n");

        List<String> filteredSummaries = Arrays.stream(separatedSummaries)
                .filter(line -> !line.trim().isEmpty()) // 공백 줄 제거한 것만
                .collect(Collectors.toList());

        log.info("ChatGPT Response: \n{}", filteredSummaries);

        List<Summary> summaries = new ArrayList<>();

        //줄바꿈으로 저장/ 리스트에 2개씩 제목, 내용 i+=2
        for (int i = 0; i < filteredSummaries.size(); i += 2) {
            if (i + 1 < filteredSummaries.size()) {
                String title = filteredSummaries.get(i).replace("### ", "").trim();
                String content = filteredSummaries.get(i + 1).trim();

                Summary summary = Summary.builder()
                        .user(user)
                        .title(title)
                        .summary(content)
                        .isDeleted(false)
                        .build();

                summaries.add(summary);
            }
        }

        summaryRespository.saveAll(summaries); //bulk insert
    }

    // saveSummary 호출하는 순간부터 3개월마다 자동으로 saveSummary 동작되도록 -> taskScheduler?
    // 해당 userId의 뉴스 요약 가져오기 (페이지네이션 적용)
    public Page<SummaryResponseDTO> getSummary(SummaryRequestDTO request) {
        QSummary summary = QSummary.summary1;
        BooleanExpression condition = summary.user.id.eq(request.getUserId()); // userId로 필터링
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        // querydsl
        List<Summary> summaries = jpaQueryFactory
                .selectFrom(summary)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //저장된 뉴스가 없을 경우
        if(summaries.isEmpty()){
            saveSummary(request.getUserId());
            return getSummary(request); //다시 요청
        }

        List<SummaryResponseDTO> summaryList = summaries.stream()
                .map(s->summaryMapper.toDTO(s))
                .collect(Collectors.toList());

        return new PageImpl<>(summaryList, pageable, summaries.size());
    }

    //해당 userId의 summary 삭제
    public void deleteSummary(Long userId) {
        summaryRespository.softDeleteByUserId(userId);
    }




}
