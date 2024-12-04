package com.team5.on_stage.subscribe.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team5.on_stage.subscribe.entity.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.team5.on_stage.subscribe.entity.QSubscribe.subscribe;

@RequiredArgsConstructor
@Repository
public class SubscribeQueryDslRepositoryImpl implements SubscribeQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Boolean existsSubscribeByUsernameAndLinkId(String username, Long linkId) {
        return jpaQueryFactory
                .selectOne()
                .from(subscribe)
                .where(subscribe.user.username.eq(username)
                        .and(subscribe.link.id.eq(linkId))
                )
                .fetchFirst() != null;
    }

    @Override
    public void deleteSubscribeByUsernameAndLinkId(String username, Long linkId) {
        jpaQueryFactory
                .delete(subscribe)
                .where(subscribe.user.username.eq(username)
                        .and(subscribe.link.id.eq(linkId))
                )
                .execute();
    }
}
