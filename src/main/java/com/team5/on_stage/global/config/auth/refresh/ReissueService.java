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
import static com.team5.on_stage.global.constants.AuthConstants.*;
import static com.team5.on_stage.global.config.jwt.JwtUtil.setErrorResponse;

@RequiredArgsConstructor
@Service
public class ReissueService {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final RefreshService refreshService;

    public void reissueRefreshToken(HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {

        /* Refresh Token 검증 */

        String oldRefreshToken = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {

                oldRefreshToken = cookie.getValue();
            }
        }

        try {
            if (oldRefreshToken == null) {
                throw new GlobalException(ErrorCode.INVALID_REFRESH_TOKEN);
            }

            if (jwtUtil.isExpired(oldRefreshToken)) {
                throw new GlobalException(ErrorCode.REFRESH_TOKEN_EXPIRED);
            }

            String tokenType = jwtUtil.getClaim(oldRefreshToken, "type");

            if (!tokenType.equals("refresh")) {
                throw new GlobalException(ErrorCode.TYPE_NOT_MATCHED);
            }

            refreshRepository.findByRefreshToken(oldRefreshToken)
                    .orElseThrow(() -> new GlobalException(ErrorCode.REFRESH_TOKEN_NOT_EXISTS));


        } catch (GlobalException e) {
            setErrorResponse(response, ErrorCode.FAILED_TO_REISSUE);
            throw new GlobalException(ErrorCode.FAILED_TO_REISSUE);
        }

        /* Refresh, Access Token 재발급 */

        String username = jwtUtil.getClaim(oldRefreshToken, "username");
        String nickname = jwtUtil.getClaim(oldRefreshToken, "nickname");
        String role = jwtUtil.getClaim(oldRefreshToken, "role");

        String newAccessToken = jwtUtil.generateAccessToken(username, nickname, role);

        String newRefreshToken = refreshService.generateRefreshToken(username, nickname, role);


        refreshService.deleteRefreshToken(oldRefreshToken);
        refreshService.saveRefreshToken(newRefreshToken, username);

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
