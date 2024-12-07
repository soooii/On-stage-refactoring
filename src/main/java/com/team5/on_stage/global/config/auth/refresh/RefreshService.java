package com.team5.on_stage.global.config.auth.refresh;

import com.team5.on_stage.global.config.redis.RedisRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.team5.on_stage.global.constants.AuthConstants.REFRESH_TOKEN_EXPIRED_MS;
import static com.team5.on_stage.global.constants.AuthConstants.TYPE_REFRESH;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshService {

    private final SecretKey secretKey;
    private final RedisRepository redisRepository;


    public String generateRefreshToken(String username,
                                       String nickname,
                                       String role) {

        return Jwts.builder()
                .claim("type", TYPE_REFRESH)
                .claim("username", username)
                .claim("nickname", nickname)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_MS))
                .signWith(secretKey)
                .compact();
    }


    // Refresh Token DB 저장
    // DB에서의 만료 시간은 Redis TTL로 관리
    @Transactional
    public void saveRefreshToken(String refreshToken, String username) {

        Refresh refresh = Refresh.builder()
                .refreshToken(refreshToken)
                .username(username)
                .build();

        redisRepository.save(refresh);
    }

    @Transactional
    public void deleteRefreshToken(String refreshToken) {

        redisRepository.findByRefreshToken(refreshToken)
                .ifPresent(token -> redisRepository.delete(token));
    }
}
