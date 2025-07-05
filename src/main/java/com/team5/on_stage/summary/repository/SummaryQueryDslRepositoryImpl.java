package com.team5.on_stage.summary.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team5.on_stage.summary.entity.Summary;
import com.team5.on_stage.summary.entity.SummaryStatus;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

import static com.team5.on_stage.summary.entity.QSummary.summary1;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SummaryQueryDslRepositoryImpl implements SummaryQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    @Transactional
    public void softDeleteByUsername(String username) {
        queryFactory
                .update(summary1)
                .set(summary1.isDeleted, true)
                .where(summary1.user.username.eq(username))
                .execute();

        em.flush();
        em.clear();

    }

    @Override
    public List<Summary> getRecentSummaryByUsername(String username, Pageable pageable) {
        JPAQuery<Summary> query = queryFactory
            .selectFrom(summary1)
            .where(summary1.user.username.eq(username))
            .where(summary1.status.eq(SummaryStatus.APPROVED))
            .where(summary1.isDeleted.isFalse())
            .orderBy(summary1.createdAt.desc());

        if (!pageable.isUnpaged()) {
            query.offset(pageable.getOffset());
            query.limit(pageable.getPageSize());
        }

        return query.fetch();
    }


    // 예전 뉴스 Summary 가져옴 (softDelete로 삭제된 것 중 최신순 정렬)
    @Override
    public List<Summary> getOldSummaryByUsername(String username, Pageable pageable) {
        return queryFactory
                .selectFrom(summary1)
                .where(summary1.user.username.eq(username))
                .where(summary1.status.eq(SummaryStatus.APPROVED))
                .where(summary1.isDeleted.isTrue())
                .orderBy(summary1.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countOldSummaryByUsername(String username) {
        return queryFactory
                .select(summary1.count())
                .from(summary1)
                .where(summary1.user.username.eq(username))
                .where(summary1.status.eq(SummaryStatus.APPROVED))
                .where(summary1.isDeleted.isTrue())
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

    @Override
    public List<Summary> getPendingSummaryByUsername(String username, Pageable pageable) {
        return queryFactory
            .selectFrom(summary1)
            .where(summary1.user.username.eq(username))
            .where(summary1.status.eq(SummaryStatus.PENDING))
            .where(summary1.isDeleted.isFalse())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    @Override
    public long countPendingSummaryByUsername(String username) {
        return queryFactory
            .select(summary1.count())
            .from(summary1)
            .where(summary1.user.username.eq(username))
            .where(summary1.status.eq(SummaryStatus.PENDING))
            .where(summary1.isDeleted.isFalse())
            .fetchOne();
    }
}
