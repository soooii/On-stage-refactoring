package com.team5.on_stage.summary.service;

import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.article.repository.ArticleRepository;
import com.team5.on_stage.article.service.ArticleService;
import com.team5.on_stage.global.config.redis.RedisService;
import com.team5.on_stage.summary.dto.SummaryRequestDTO;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;

import com.team5.on_stage.summary.entity.Summary;
import com.team5.on_stage.summary.entity.SummaryStatus;
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

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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

    // username별로 닉네임 정보 캐싱
    private final Map<String, String> userNicknameCache = new ConcurrentHashMap<>();

    // 해당 username의 summary 저장
    public void saveSummary(String username) {

        //기존 Summary soft delete
        summaryRespository.softDeleteByUsername(username);

        articleService.save(username);
        //articleService.firstFilteredArticles(username);

        List<Article> articles = articleRepository.findAllByUser_Username(username);

        //if(articles.isEmpty()){return;}

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
    }

    //1. Redis 사용
    public Page<SummaryResponseDTO> getRecentSummary(SummaryRequestDTO request) {
        String username = request.getUsername();

        // 닉네임 변경 여부 체크
        if (redisService.isNicknameChanged(username)) {
            log.info("닉네임 변경 감지됨, 요약 새로 생성");

            // 기존 summary 캐시 삭제
            redisService.deleteSummaryCache(username);

            // 요약 새로 생성 저장
            saveSummary(username);

            // 새로 저장한 summary 전체를 DB에서 조회 후 Redis에 캐싱
            List<SummaryResponseDTO> freshSummaries = summaryRespository.getRecentSummaryByUsername(username, Pageable.unpaged())
                .stream()
                .map(summaryMapper::toDTO)
                .collect(Collectors.toList());

            redisService.setSummaryCache(username, freshSummaries, Duration.ofHours(24));
        }

        // Redis 캐시에서 요약 데이터 조회
        List<SummaryResponseDTO> cachedSummaries = redisService.getSummaryCache(username);

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        if (cachedSummaries != null) {
            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), cachedSummaries.size());

            if (start > end) {
                // 요청 페이지 범위가 캐시 데이터 범위 밖일 경우 빈 페이지 반환
                return new PageImpl<>(Collections.emptyList(), pageable, cachedSummaries.size());
            }

            List<SummaryResponseDTO> pageContent = cachedSummaries.subList(start, end);
            return new PageImpl<>(pageContent, pageable, cachedSummaries.size());
        } else {
            // 캐시 미스 시 DB에서 데이터 조회 후 캐싱
            List<Summary> summaries = summaryRespository.getRecentSummaryByUsername(username, pageable);

            if (summaries.isEmpty()) {
                log.info("해당 유저의 뉴스가 없습니다: {}", username);
                return new PageImpl<>(Collections.emptyList(), pageable, 0);
            }

            List<SummaryResponseDTO> summaryList = summaries.stream()
                .map(summaryMapper::toDTO)
                .collect(Collectors.toList());

            // 전체 데이터를 별도로 모두 조회해서 캐싱
            List<SummaryResponseDTO> allSummaries = summaryRespository.getRecentSummaryByUsername(username, Pageable.unpaged())
                .stream()
                .map(summaryMapper::toDTO)
                .collect(Collectors.toList());

            redisService.setSummaryCache(username, allSummaries, Duration.ofHours(24));

            return new PageImpl<>(summaryList, pageable, allSummaries.size());
        }
    }


    //2.Redis 미사용
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
}
