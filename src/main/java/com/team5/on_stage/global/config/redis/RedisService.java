package com.team5.on_stage.global.config.redis;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;
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
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

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

    public void setSummaryCache(String username, List<SummaryResponseDTO> summaries, Duration ttl) {
        String key = "SummaryCache:" + username;
        try {
            String json = objectMapper.writeValueAsString(summaries);
            redisTemplate.opsForValue().set(key, json, ttl);
        } catch (JsonProcessingException e) {
            log.error("SummaryCache 직렬화 오류", e);
        }
    }

    public List<SummaryResponseDTO> getSummaryCache(String username) {
        String key = "SummaryCache:" + username;
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) return null;

        try {
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, SummaryResponseDTO.class);
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error("SummaryCache 역직렬화 오류", e);
            return null;
        }
    }


    public void deleteSummaryCache(String username) {
        String key = "SummaryCache:" + username;
        redisTemplate.delete(key);
    }

    public boolean isNicknameChanged(String username) {
        String prevNicknameKey = "PrevUserNickname:" + username;
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        String prevNickname = ops.get(prevNicknameKey);
        String currentNickname = getUserNickname(username);

        if (prevNickname == null || !prevNickname.equals(currentNickname)) {
            ops.set(prevNicknameKey, currentNickname, Duration.ofDays(7));
            return true;
        }
        return false;
    }











}
