package com.team5.on_stage.global.config.redis;

import java.time.Duration;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryCacheService {
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

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
}
