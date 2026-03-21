package com.team5.on_stage.summary.service;

import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.article.repository.ArticleRepository;
import com.team5.on_stage.article.service.ArticleService;
import com.team5.on_stage.global.config.redis.RedisService;
import com.team5.on_stage.global.config.redis.SummaryCacheService;
import com.team5.on_stage.summary.dto.SummaryRequestDTO;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;

import com.team5.on_stage.summary.entity.Summary;
import com.team5.on_stage.summary.entity.SummaryStatus;
import com.team5.on_stage.summary.mapper.SummaryMapper;
import com.team5.on_stage.summary.repository.SummaryRespository;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import com.team5.on_stage.util.chatGPT.service.ChatGPTService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SummaryService {
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;
    private final SummaryRespository summaryRespository;
    private final SummaryMapper summaryMapper;
    private final ChatGPTService chatGPTService;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final SummaryCacheService summaryCacheService;

    // 해당 username의 summary 저장
    @Async
    @Transactional
    public void saveSummary(String username) {

        //기존 Summary soft delete
        summaryRespository.softDeleteByUsername(username);

        articleService.save(username);

        List<Article> articles = articleRepository.findAllByUser_Username(username);

        User user = userRepository.findByUsername(username);
        String nickname = user.getNickname();
        String allArticles = articles.stream()
                .map(article -> article.getContent())
                .collect(Collectors.joining());

        String prompt = """
            부정적이거나 논란이 될 수 있는 주제는 제외하고,
            '%s'와 직접 관련된 뉴스 4개를,
            각 제목은 번호 없이, 7문장 이내로 작성해줘.
            뉴스만 바로 출력해줘, 불필요한 인사말은 빼줘.
            내용: %s
            """.formatted(nickname, allArticles);


        String summarizedNews = chatGPTService.sendMessage(prompt);

        log.info("ChatGPT Response: \n{}", summarizedNews);

        String[] separatedSummaries = summarizedNews.split("\n");

        List<String> filteredSummaries = Arrays.stream(separatedSummaries)
                .filter(line -> !line.trim().isEmpty()) // 공백 줄 제거한 것만
                .collect(Collectors.toList());

        log.info("ChatGPT Response: \n{}", filteredSummaries);

        List<Summary> summaries = new ArrayList<>();

        // 줄바꿈으로 저장 / 리스트에 2개씩 제목, 내용 i+=2
        for (int i = 0; i < filteredSummaries.size(); i += 2) {
            if (i + 1 < filteredSummaries.size()) {
                String title = filteredSummaries.get(i)
                        .replaceAll("^[0-9.\\s#*]+", "")
                        .replaceAll("[#*]+$", "")
                        .trim();
                String content = filteredSummaries.get(i + 1).trim();

                Summary summary = Summary.builder()
                        .user(user)
                        .title(title)
                        .summary(content)
                        .isDeleted(false)
                        .status(SummaryStatus.PENDING)
                        .build();

                summaries.add(summary);
            }
        }

        summaryRespository.saveAll(summaries); //bulk insert
        summaryCacheService.evictSummaryCache(username);
    }

    public Page<SummaryResponseDTO> getRecentSummary(SummaryRequestDTO request) {
        String username = request.getUsername();
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        // Redis
        List<SummaryResponseDTO> cachedSummaries = summaryCacheService.getSummaryCache(username);

        if (cachedSummaries != null) {
            log.info("[CACHE HIT] Redis에서 데이터 반환");
            return getPageImpl(cachedSummaries, pageable);
        }

        log.info("[CACHE MISS] DB에서 데이터 조회");


        List<Summary> approvedList = summaryRespository.getRecentSummaryByUsername(username, Pageable.unpaged());

        if (approvedList.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<SummaryResponseDTO> dtoList = approvedList.stream()
            .map(summaryMapper::toDTO)
            .collect(Collectors.toList());

        summaryCacheService.setSummaryCache(username, dtoList, Duration.ofHours(12));

        return getPageImpl(dtoList, pageable);
    }

    public Page<SummaryResponseDTO> getRecentSummaryWithoutCache(SummaryRequestDTO request) {
        String username = request.getUsername();

        if (redisService.isNicknameChanged(username)) {
            log.info("닉네임 변경 감지됨, 요약 새로 생성");

            // 기존 요약 삭제 및 새로 생성
            summaryRespository.softDeleteByUsername(username);
            saveSummary(username);
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        List<Summary> summaries = summaryRespository.getRecentSummaryByUsername(username, pageable);

        if (summaries.isEmpty()) {
            log.info("해당 유저의 뉴스가 없습니다: {}", username);
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<SummaryResponseDTO> summaryList = summaries.stream()
            .map(summaryMapper::toDTO)
            .collect(Collectors.toList());


        return new PageImpl<>(summaryList, pageable, 4);
    }


    public Page<SummaryResponseDTO> getOldSummary(SummaryRequestDTO request) {
        String username = request.getUsername();
        User user = userRepository.findByUsername(username);
        String currentNickname = user.getNickname();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        List<Summary> summaries = summaryRespository.getOldSummaryByUsername(username, pageable);

        if (summaries.isEmpty()) {
            log.info("해당 유저의 예전 뉴스가 아직 없습니다: {}", currentNickname);
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<SummaryResponseDTO> summaryList = summaries.stream()
                .map(summaryMapper::toDTO)
                .collect(Collectors.toList());

        // 해당 username의 전체 뉴스 요약 개수
        long total = summaryRespository.countOldSummaryByUsername(username);

        return new PageImpl<>(summaryList, pageable, total);
    }

    //해당 userId의 summary 삭제
    public void deleteSummary(String username) {
        summaryRespository.softDeleteByUsername(username);
        summaryCacheService.evictSummaryCache(username);
    }

    @Transactional
    public void clearSummaryHistoryForNicknameChange(String username) {
        summaryRespository.deleteAllByUsername(username);
        summaryCacheService.evictSummaryCache(username);
    }

    @Transactional
    public boolean approveSummary(Long summaryId) {
        Optional<Summary> summaryOpt = summaryRespository.findById(summaryId);
        if (summaryOpt.isEmpty()) {
            return false;
        }

        Summary summary = summaryOpt.get();
        summaryRespository.save(summary.updateStatus(SummaryStatus.APPROVED));
        summaryCacheService.evictSummaryCache(summary.getUser().getUsername());
        return true;
    }

    @Transactional
    public boolean rejectSummary(Long summaryId) {
        Optional<Summary> summaryOpt = summaryRespository.findById(summaryId);
        if (summaryOpt.isEmpty()) {
            return false;
        }

        Summary summary = summaryOpt.get();
        summaryRespository.save(summary.updateStatus(SummaryStatus.REJECTED));
        summaryCacheService.evictSummaryCache(summary.getUser().getUsername());
        return true;
    }

    public Page<SummaryResponseDTO> getPendingSummary(SummaryRequestDTO request) {
        String username = request.getUsername();
        User user = userRepository.findByUsername(username);
        String currentNickname = user.getNickname();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        List<Summary> summaries = summaryRespository.getPendingSummaryByUsername(username, pageable);

        if (summaries.isEmpty()) {
            log.info("해당 유저의 검수 요청 뉴스가 없습니다: {}", currentNickname);
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<SummaryResponseDTO> summaryList = summaries.stream()
            .map(summaryMapper::toDTO)
            .collect(Collectors.toList());

        long total = summaryRespository.countPendingSummaryByUsername(username);

        return new PageImpl<>(summaryList, pageable, total);
    }

    /**
     * Redis에서 가져온 전체 리스트를 Page 객체로 변환해주는 헬퍼 메서드
     */
    private Page<SummaryResponseDTO> getPageImpl(List<SummaryResponseDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        // 시작 지점이 리스트 크기보다 크면 빈 페이지 반환 (예: 데이터는 5개인데 100페이지 요청 시)
        if (start > list.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, list.size());
        }

        // 리스트를 시작~끝 지점만큼만 잘라서 Page 객체로 생성
        List<SummaryResponseDTO> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageable, list.size());
    }
}
