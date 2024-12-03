package com.team5.on_stage.global.config.jwt;

import com.team5.on_stage.user.entity.Refresh;
import com.team5.on_stage.user.repository.RefreshRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;

import static com.team5.on_stage.global.config.jwt.AuthConstants.*;

@RequiredArgsConstructor
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final RefreshRepository refreshRepository;


    // JWT 생성
    public String generateAccessToken(String username,
                                      String nickname,
                                      String role) {

        LocalDateTime now = LocalDateTime.now();

        return Jwts.builder()
                .claim("type", TYPE_ACCESS)
                .claim("username", username)
                .claim("nickname", nickname)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_MS))
                .signWith(secretKey)
                .compact();
    }


    public String generateRefreshToken(String username,
                                       String nickname,
                                       String role) {

        LocalDateTime now = LocalDateTime.now();

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
    public void addRefresh(String username,
                           String refreshToken) {

        Date date = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_MS);

        Refresh newRefreshToken = new Refresh();
        newRefreshToken.setUsername(username);
        newRefreshToken.setRefresh(refreshToken);
        newRefreshToken.setExpiration(date.toString());

        refreshRepository.save(newRefreshToken);
    }


    public String getClaim(String token, String claim) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(claim, String.class);
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
