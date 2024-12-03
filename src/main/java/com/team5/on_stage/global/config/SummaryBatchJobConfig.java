package com.team5.on_stage.global.config;

import com.team5.on_stage.summary.repository.SummaryRespository;
import com.team5.on_stage.summary.service.SummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SummaryBatchJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final SummaryService summaryService;
    private final SummaryRespository summaryRespository;

    // Save Summary Job 정의
    @Bean
    public Job saveSummaryJob(Step saveSummaryStep) {
        return new JobBuilder("saveSummaryJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(saveSummaryStep)
                .build();
    }

    // Save Summary Step 정의
    @Bean
    public Step saveSummaryStep() {
        Tasklet saveSummaryTasklet = (contribution, chunkContext) -> {
            List<String> usernames = getUsersWithOldSummaries();

            for (String username : usernames) {
                summaryService.saveSummary(username); //새로 요약 저장
                log.info("{}의 summary 새로 저장", username);
            }

            return RepeatStatus.FINISHED;
        };

        return new StepBuilder("saveSummaryStep", jobRepository)
                .tasklet(saveSummaryTasklet, transactionManager)
                .build();
    }

    private List<String> getUsersWithOldSummaries() {
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);

        // DB에서 3개월 이상 업데이트되지 않은 사용자 목록 조회
        return summaryRespository.findUsernamesWithOldSummaries(threeMonthsAgo);
    }
}


