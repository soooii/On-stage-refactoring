package com.team5.on_stage.global.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setValue(String key, String value, Duration duration) {

        ValueOperations<String, Object> data = redisTemplate.opsForValue();
        data.set(key, value, duration);
    }
}
