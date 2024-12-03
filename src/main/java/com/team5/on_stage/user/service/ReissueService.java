package com.team5.on_stage.user.service;

import com.team5.on_stage.global.config.jwt.JwtUtil;
import com.team5.on_stage.user.repository.RefreshRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.team5.on_stage.global.config.auth.cookie.CookieUtil.createCookie;
import static com.team5.on_stage.global.config.auth.cookie.CookieUtil.deleteCookie;
import static com.team5.on_stage.global.config.jwt.AuthConstants.*;

@RequiredArgsConstructor
@Service
public class ReissueService {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public void reissueRefreshToken(HttpServletRequest request,
                                      HttpServletResponse response) {

        String refreshToken = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {

                refreshToken = cookie.getValue();
            }
        }

        // Todo: 예외처리
        if (refreshToken == null) {
            throw new JwtException("Refresh token is empty");
        }

        if (jwtUtil.isExpired(refreshToken)) {
            throw new JwtException("Refresh token is expired");
        }

        String tokenType = jwtUtil.getClaim(refreshToken, "type");
        if (!tokenType.equals("refresh")) {
            throw new JwtException("Refresh token is not a refresh token");
        }

        if (!refreshRepository.existsByRefresh(refreshToken)) {
            throw new JwtException("Refresh token does not exist");
        }

        String username = jwtUtil.getClaim(refreshToken, "username");
        String role = jwtUtil.getClaim(refreshToken, "role");

        String newAccessToken = jwtUtil.generateAccessToken(username, role);

        String newRefreshToken = jwtUtil.generateRefreshToken(username, role);

        refreshRepository.deleteByRefresh(refreshToken);
        jwtUtil.addRefresh(username, newRefreshToken);

        Cookie deleteRefreshToken = deleteCookie("refresh");

        response.setHeader(AUTH_HEADER, AUTH_TYPE + newAccessToken);
        Cookie deleteAccessToken = deleteCookie("access");
        Cookie deleteJSessionCookie = deleteCookie("JSESSIONID");

        response.addCookie(deleteRefreshToken);
        response.addCookie(deleteAccessToken);
        response.addCookie(deleteJSessionCookie);

        request.getSession().invalidate();

        response.addCookie(createCookie("refresh", newRefreshToken, true));
        response.addCookie(createCookie("access", newAccessToken, false));
    }
}
