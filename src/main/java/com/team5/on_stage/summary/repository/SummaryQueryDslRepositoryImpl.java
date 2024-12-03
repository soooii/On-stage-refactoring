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
                .where(summary1.user.name.eq(username))
                .fetchOne();
    }
}
