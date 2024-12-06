package com.team5.on_stage.global.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedisRefreshService {

    private final RedisRefreshRepository redisRefreshRepository;

    @Transactional
    public void saveRefreshToken(String username, String refreshToken) {

        RedisRefresh redisRefresh = RedisRefresh.builder()
                .refreshToken(refreshToken)
                .username(username)
                .build();

        redisRefreshRepository.save(redisRefresh);
    }

    @Transactional
    public void removeRefreshToken(String refreshToken) {

        redisRefreshRepository.findByRefreshToken(refreshToken)
                .ifPresent(token -> redisRefreshRepository.delete(token));
    }
}
