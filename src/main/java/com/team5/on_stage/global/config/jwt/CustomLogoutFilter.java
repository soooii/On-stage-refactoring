package com.team5.on_stage.global.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.on_stage.user.repository.RefreshRepository;
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
import java.util.Map;

import static com.team5.on_stage.global.config.auth.cookie.CookieUtil.deleteCookie;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
    }

    private void doFilter(HttpServletRequest request,
                         HttpServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        // URI 검증
        if (!request.getRequestURI().startsWith("/logout")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Http Method 검증
        if (!request.getMethod().equals("POST")) {

            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = null;

        // Todo: 예외처리
        try {
            Cookie[] cookies = request.getCookies();

//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("token")) {
//                    String decodedTokens = cookie.getValue();
//
//                    ObjectMapper mapper = new ObjectMapper();
//
//                    Map<String, String> tokens = mapper.readValue(decodedTokens, Map.class);
//
//                    refreshToken = tokens.get("refresh");
//                }
//            }

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refreshToken = cookie.getValue();
                }
            }


        } catch (Exception e) {
            throw new ServletException("Failed to refresh token", e);
        }

        // Todo: 예외처리
        // 토큰 존재 여부 확인
        if (refreshToken == null) {
            throw new ServletException("Invalid refresh token");
        }

        // Todo: 예외처리, 필요성
        // 토큰 만료 여부 검증
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (Exception e) {
            throw new ServletException("Expired refresh token", e);
        }

        // Todo: 예외처리
        // 토큰 타입 검증
        if (!jwtUtil.getType(refreshToken).equals("refresh")) {
            throw new ServletException("Invalid refresh token");
        }

        // Todo: 예외처리
        // 토큰 DB 존재 여부 확인
        if (!refreshRepository.existsByRefresh(refreshToken)) {
            throw new ServletException("Refresh token does not exist");
        }

        /* 로그아웃 */

        refreshRepository.deleteByRefresh(refreshToken);

        // Todo: 쿠키의 토큰 삭제는 프론트에서 처리하면 안될까?
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
