package com.team5.on_stage.summary.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team5.on_stage.summary.entity.Summary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team5.on_stage.summary.entity.QSummary.summary1;

@Repository
@RequiredArgsConstructor
public class SummaryQueryDslRepositoryImpl implements SummaryQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public void softDeleteByUserId(Long userId) {
        queryFactory
                .update(summary1)
                .set(summary1.isDeleted, true)
                .where(summary1.user.id.eq(userId))
                .execute();
    }

    @Override
    public List<Summary> getSummaryByUserId(Long userId, Pageable pageable) {
        return queryFactory
                .selectFrom(summary1)
                .where(summary1.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long countSummaryByUserId(Long userId) {
        return queryFactory
                .select(summary1.count())
                .from(summary1)
                .where(summary1.user.id.eq(userId))
                .fetchOne();
    }
}
