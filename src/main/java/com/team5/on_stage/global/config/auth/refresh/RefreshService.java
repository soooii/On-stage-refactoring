package com.team5.on_stage.global.config.auth.refresh;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshService {

    private final RefreshRepository refreshRepository;



    // Refresh Token DB 저장
    // DB에서의 만료 시간은 Redis TTL로 관리
    public void addRefresh(String refreshToken,
                           String username) {

        Refresh newRefreshToken = Refresh.builder()
                .refreshToken(refreshToken)
                .username(username)
                .build();

        refreshRepository.save(newRefreshToken);
    }

    @Transactional
    public void saveRefreshToken(String refreshToken, String username) {

        Refresh refresh = Refresh.builder()
                .refreshToken(refreshToken)
                .username(username)
                .build();

        refreshRepository.save(refresh);
    }

    @Transactional
    public void deleteRefreshToken(String refreshToken) {

        refreshRepository.findByRefreshToken(refreshToken)
                .ifPresent(token -> refreshRepository.delete(token));
    }
}
