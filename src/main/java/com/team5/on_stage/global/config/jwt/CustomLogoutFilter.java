package com.team5.on_stage.global.config.jwt;

import com.team5.on_stage.global.config.auth.refresh.RefreshService;
import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.global.config.auth.refresh.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.team5.on_stage.global.config.auth.cookie.CookieUtil.deleteCookie;
import static com.team5.on_stage.global.config.jwt.JwtUtil.setErrorResponse;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final RefreshService refreshService;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
    }

    private void doFilter(HttpServletRequest request,
                         HttpServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        /* Request 검증 */

        if (!request.getRequestURI().startsWith("/logout")) {

            filterChain.doFilter(request, response);
            return;
        }

        if (!request.getMethod().equals("POST")) {

            filterChain.doFilter(request, response);
            return;
        }

        /* 토큰 검증 */

        String refreshToken = null;

        try {
            Cookie[] cookies = request.getCookies();

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refreshToken = cookie.getValue();
                }
            }

        } catch (Exception e) {
            setErrorResponse(response, ErrorCode.BAD_REQUEST);
            throw new GlobalException(ErrorCode.BAD_REQUEST);
        }

        if (refreshToken == null) {
            setErrorResponse(response, ErrorCode.BAD_REQUEST);
            throw new GlobalException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // Todo: 필요성
        if (jwtUtil.isExpired(refreshToken)) {
            setErrorResponse(response, ErrorCode.REFRESH_TOKEN_EXPIRED);
            throw new GlobalException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        if (!jwtUtil.getClaim(refreshToken, "type").equals("refresh")) {
            setErrorResponse(response, ErrorCode.TYPE_NOT_MATCHED);
            throw new GlobalException(ErrorCode.TYPE_NOT_MATCHED);
        }

        if (refreshRepository.findByRefreshToken(refreshToken).isEmpty()) {
            setErrorResponse(response, ErrorCode.REFRESH_TOKEN_NOT_EXISTS);
            throw new GlobalException(ErrorCode.REFRESH_TOKEN_NOT_EXISTS);
        }

        /* 로그아웃 */

        refreshService.deleteRefreshToken(refreshToken);

        Cookie deleteRefreshCookie = deleteCookie("refresh");
        Cookie deleteAccessCookie = deleteCookie("access");
        Cookie deleteJSessionCookie = deleteCookie("JSESSIONID");

        response.addCookie(deleteRefreshCookie);
        response.addCookie(deleteAccessCookie);
        response.addCookie(deleteJSessionCookie);

        request.getSession().invalidate();

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
