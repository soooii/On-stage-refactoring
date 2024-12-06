package com.team5.on_stage.global.config.redis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisRefreshRepository extends CrudRepository<RedisRefresh, String>, KeyValueRepository<RedisRefresh, String> {

    Optional<RedisRefresh> findByRefreshToken(String refreshToken);
}
