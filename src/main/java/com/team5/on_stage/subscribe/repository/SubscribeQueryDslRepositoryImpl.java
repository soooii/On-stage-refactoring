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
    public Boolean existsSubscribeBySubscriberAndSubscribed(String subscriber, String subscribed) {
        return jpaQueryFactory
                .selectOne()
                .from(subscribe)
                .where(subscribe.subscriber.username.eq(subscriber)
                        .and(subscribe.subscribed.username.eq(subscribed))
                )
                .fetchFirst() != null;
    }

    @Override
    public void deleteSubscribeBySubscriberAndSubscribed(String subscriber, String subscribed) {
        jpaQueryFactory
                .delete(subscribe)
                .where(subscribe.subscriber.username.eq(subscriber)
                        .and(subscribe.subscribed.username.eq(subscribed))
                )
                .execute();
    }
}
