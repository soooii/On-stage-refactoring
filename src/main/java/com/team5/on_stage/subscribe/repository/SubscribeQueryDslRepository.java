package com.team5.on_stage.subscribe.repository;

import com.team5.on_stage.subscribe.entity.Subscribe;

public interface SubscribeQueryDslRepository {

    Boolean existsSubscribeBySubscriberAndSubscribed(String subscriber, String subscribed);

    void deleteSubscribeBySubscriberAndSubscribed(String subscriber, String subscribed);
}
