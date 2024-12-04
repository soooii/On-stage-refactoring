package com.team5.on_stage.subscribe.repository;

import com.team5.on_stage.subscribe.entity.Subscribe;

public interface SubscribeQueryDslRepository {

    Boolean existsSubscribeByUsernameAndLinkId(String username, Long linkId);

    void deleteSubscribeByUsernameAndLinkId(String username, Long linkId);
}
