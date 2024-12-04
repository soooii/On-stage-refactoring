package com.team5.on_stage.summary.repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team5.on_stage.summary.entity.Summary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

import static com.team5.on_stage.summary.entity.QSummary.summary1;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SummaryQueryDslRepositoryImpl implements SummaryQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public void softDeleteByUsername(String username) {
        queryFactory
                .update(summary1)
                .set(summary1.isDeleted, true)
                .where(summary1.user.username.eq(username))
                .execute();
    }

    @Override
    public List<Summary> getSummaryByUsername(String username, Pageable pageable) {
        return queryFactory
                .selectFrom(summary1)
                .where(summary1.user.username.eq(username))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countSummaryByUsername(String username) {
        return queryFactory
                .select(summary1.count())
                .from(summary1)
                .where(summary1.user.username.eq(username))
                .fetchOne();
    }

    @Override
    public List<String> findUsernamesWithOldSummaries(LocalDateTime timeToCompare) {
       return queryFactory
                .select(summary1.user.username)
                .from(summary1)
                .join(summary1.user)
                .where(summary1.createdAt.loe(timeToCompare))
                .groupBy(summary1.user.username)
               .fetch();
    }
}
