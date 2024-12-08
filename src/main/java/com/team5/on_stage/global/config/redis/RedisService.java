package com.team5.on_stage.global.config.redis;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;


    /* Refresh Token */

    // Todo: Transactional 어노테이션 필요성
    public void setRefreshToken(String username, String refreshToken) {

        String key = "RefreshToken: " + username;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        ops.set(key, refreshToken, Duration.ofHours(24));
    }

    public String getRefreshToken(String refreshToken) {

        String key = "RefreshToken: " + refreshToken;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        if (ops.get(key) == null) {
            return null;
        }

        return ops.get(key);
    }

    public void deleteRefreshToken(String refreshToken) {

        redisTemplate.delete(refreshToken);
    }

    /* Verification Code */

    public void setVerificationCode(String username, String verificationCode) {

        String key = "VerificationCode: " + verificationCode;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        ops.set(key, username, Duration.ofMinutes(5));
    }

    public String getVerificationCode(String verificationCode) {

        String key =  "VerificationCode: " + verificationCode;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        if (ops.get(key) == null) {
            return null;
        }

        return ops.get(key);
    }

    public void deleteVerificationCode(String verificationCode) {

        redisTemplate.delete(verificationCode);
    }

    public void setExpireValue(String key, Long timeout) {

        redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
    }

//    public void setHashOps(String key, Map<String, String> data) {
//
//        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
//        values.putAll(key, data);
//    }
//
//    @Transactional(readOnly = true)
//    public String getHashOps(String key, String hashKey) {
//        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
//        return Boolean.TRUE.equals(values.hasKey(key, hashKey)) ? (String) redisTemplate.opsForHash().get(key, hashKey) : "";
//    }
//
//    public void deleteHashOps(String key, String hashKey) {
//        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
//        values.delete(key, hashKey);
//    }

    public boolean checkExistsValue(String value) {
        return !value.equals("false");
    }







}
