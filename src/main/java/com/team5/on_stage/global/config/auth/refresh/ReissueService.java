package com.team5.on_stage.global.config.auth.refresh;

import com.team5.on_stage.global.config.jwt.JwtUtil;
import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.team5.on_stage.global.config.auth.cookie.CookieUtil.createCookie;
import static com.team5.on_stage.global.config.auth.cookie.CookieUtil.deleteCookie;
import static com.team5.on_stage.global.config.jwt.AuthConstants.*;
import static com.team5.on_stage.global.config.jwt.JwtUtil.setErrorResponse;

@RequiredArgsConstructor
@Service
public class ReissueService {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public void reissueRefreshToken(HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {

        String refreshToken = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {

                refreshToken = cookie.getValue();
            }
        }

        // Todo: 예외처리
        try {
            if (refreshToken == null) {
                throw new GlobalException(ErrorCode.INVALID_REFRESH_TOKEN);
            }

            if (jwtUtil.isExpired(refreshToken)) {
                throw new GlobalException(ErrorCode.REFRESH_TOKEN_EXPIRED);
            }

            String tokenType = jwtUtil.getClaim(refreshToken, "type");

            if (!tokenType.equals("refresh")) {
                throw new GlobalException(ErrorCode.TYPE_NOT_MATCHED);
            }

            if (!refreshRepository.existsByRefresh(refreshToken)) {
                throw new GlobalException(ErrorCode.REFRESH_TOKEN_NOT_EXISTS);
            }
        } catch (GlobalException e) {
            setErrorResponse(response, ErrorCode.FAILED_TO_REISSUE);
            throw new GlobalException(ErrorCode.FAILED_TO_REISSUE);
        }

        String username = jwtUtil.getClaim(refreshToken, "username");
        String nickname = jwtUtil.getClaim(refreshToken, "nickname");
        String role = jwtUtil.getClaim(refreshToken, "role");

        String newAccessToken = jwtUtil.generateAccessToken(username, nickname, role);

        String newRefreshToken = jwtUtil.generateRefreshToken(username, nickname, role);

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
