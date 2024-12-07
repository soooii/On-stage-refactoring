package com.team5.on_stage.global.config.redis;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;


    public void setValue(String key, String value, Duration duration) {

        ValueOperations<String, String> data = redisTemplate.opsForValue();
        data.set(key, value, duration);
    }

    public void setVerificationCode(String verificationCode, String username, Long timeout) {

        ValueOperations<String, String> verificationData = redisTemplate.opsForValue();

        verificationData.set(verificationCode, username, timeout, TimeUnit.MINUTES);
    }

    @Transactional
    public String getValue(String key) {

        ValueOperations<String, String> data = redisTemplate.opsForValue();

        if (data.get(key) == null) {
            return "false";
        }

        return data.get(key);
    }

    public void setExpireValue(String key, Long timeout) {

        redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
    }

    public void deleteValue(String key) {

        redisTemplate.delete(key);
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
