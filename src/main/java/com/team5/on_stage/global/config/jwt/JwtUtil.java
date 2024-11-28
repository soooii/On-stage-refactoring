package com.team5.on_stage.global.config.jwt;

import com.team5.on_stage.user.entity.Refresh;
import com.team5.on_stage.user.repository.RefreshRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtUtil implements AuthConstants {

    private final SecretKey secretKey;
    private final RefreshRepository refreshRepository;

    public JwtUtil(@Value("${JWT_SECRET_KEY}") String secret,
                   RefreshRepository refreshRepository) {

        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.refreshRepository = refreshRepository;
    }


    // JWT 생성
    public String generateToken(String type,
                                String username,
                                String role,
                                Long expiredMs) {

        LocalDateTime now = LocalDateTime.now();

        return Jwts.builder()
                .claim("type", type)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();

    }

    // Refresh Token DB 저장
    public void addRefresh(String username,
                           String refreshToken) {

        Date date = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_MS);

        Refresh newRefreshToken = new Refresh();
        newRefreshToken.setUsername(username);
        newRefreshToken.setRefresh(refreshToken);
        newRefreshToken.setExpiration(date.toString());

        refreshRepository.save(newRefreshToken);
    }

    // username 추출
    public String getUsername(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    // role 추출
    public String getRole(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    // type 추출 (access / refresh)
    public String getType(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("type", String.class);
    }

    // 만료 시간 추출
    public Date getExpires(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    public Boolean isExpired(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }
}
