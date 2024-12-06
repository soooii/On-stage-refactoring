package com.team5.on_stage.global.config.auth.refresh;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshRepository extends CrudRepository<Refresh, String>, KeyValueRepository<Refresh, String> {

    Optional<Refresh> findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}
