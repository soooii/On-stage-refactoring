package com.team5.on_stage.global.config.redis;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCacheService {
	private final UserRepository userRepository;

	@Cacheable(value = "userNicknameCache", key = "#username")
	public String getUserNickname(String username) {
		User user = userRepository.findByUsername(username);
		return user.getNickname();
	}
}
