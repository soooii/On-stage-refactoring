package com.team5.on_stage.global.config.redis;

import com.team5.on_stage.global.config.auth.refresh.Refresh;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisRepository extends CrudRepository<Refresh, String>, KeyValueRepository<Refresh, String> {

    Optional<Refresh> findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}
