package com.team5.on_stage.global.config.auth.refresh;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshService {

    private final RefreshRepository refreshRepository;

    @Transactional
    public void saveRefreshToken(String username, String refreshToken) {

        Refresh refresh = Refresh.builder()
                .refreshToken(refreshToken)
                .username(username)
                .build();

        refreshRepository.save(refresh);
    }

    @Transactional
    public void removeRefreshToken(String refreshToken) {

        refreshRepository.findByRefreshToken(refreshToken)
                .ifPresent(token -> refreshRepository.delete(token));
    }
}
