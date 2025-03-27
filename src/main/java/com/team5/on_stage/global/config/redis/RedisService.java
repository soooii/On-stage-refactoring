package com.team5.on_stage.global.config.redis;


import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    /* Refresh Token */

    // Todo: Transactional 어노테이션 필요성
    // Todo: 현재 키와 값이 동일한 형태. 다른 저장 형태를 고민해볼 것
    public void setRefreshToken(String refreshToken, String username) {

        String key = "RefreshToken:" + refreshToken;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        ops.set(key, refreshToken, Duration.ofHours(24));
    }

    public String getRefreshToken(String refreshToken) {

        String key = "RefreshToken:" + refreshToken;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        if (ops.get(key) == null) {
            return null;
        }

        return ops.get(key);
    }

    public void deleteRefreshToken(String refreshToken) {

        String key = "RefreshToken:" + refreshToken;

        redisTemplate.delete(key);
    }

    /* Verification Data */

    public void setSmsVerificationData(String username,
                                       String verificationData,
                                       String requestTime) {

        String key = "SMS:VerificationData:" + username + ":" + requestTime;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        ops.set(key, verificationData, Duration.ofMinutes(5));
    }

    public String getVerificationData(String username,
                                      String requestTime) {

        String key = "SMS:VerificationData:" + username + ":" + requestTime;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        if (ops.get(key) == null) {
            return null;
        }

        return ops.get(key);
    }

    public void deleteVerificationData(String username,
                                       String requestTime) {

        String key = "SMS:VerificationData:" + username + ":" + requestTime;

        redisTemplate.delete(key);
    }

    /* Verification Request Time */

    public void setSmsVerificationRequestTime(String username,
                                              String phoneNumber,
                                              String requestTime) {

        String key = "SMS:RequestTime:" + username + ":" + phoneNumber;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        ops.set(key, requestTime, Duration.ofMinutes(5));
    }

    public String getSmsVerificationRequestTime(String username,
                                                String phoneNumber) {

        String key = "SMS:RequestTime:" + username + ":" + phoneNumber;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        if (ops.get(key) == null) {
            return null;
        }

        return ops.get(key);
    }

    public void deleteVerificationRequestTime(String username,
                                              String phoneNumber) {

        String key = "SMS:RequestTime:" + username + ":" + phoneNumber;

        redisTemplate.delete(key);
    }

    @Cacheable(value = "userNicknameCache", key = "#username")
    public String getUserNickname(String username) {
        User user = userRepository.findByUsername(username);
        return user.getNickname();
    }

    @CachePut(value = "userNicknameCache", key = "#username")
    public String updateUserNicknameCache(String username, String newNickname) {
        return newNickname;
    }






}
