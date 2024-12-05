package com.team5.on_stage.global.config.jwt;

import com.team5.on_stage.global.config.auth.dto.CustomOAuth2User;
import com.team5.on_stage.global.config.auth.dto.UserDto;
import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.user.entity.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.team5.on_stage.global.config.jwt.JwtUtil.setErrorResponse;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        /* Access Token 검증 */

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorizationHeader.split(" ")[1];

        if (accessToken == null) {
            setErrorResponse(response, ErrorCode.INVALID_ACCESS_TOKEN);
            return;
        }

        String type = jwtUtil.getClaim(accessToken, "type");

        if (type == null || !type.equals("access")) {
            setErrorResponse(response, ErrorCode.TYPE_NOT_MATCHED);
            return;
        }

        if (jwtUtil.isExpired(accessToken)) {
            setErrorResponse(response, ErrorCode.ACCESS_TOKEN_EXPIRED);
            return;
        }

        /* 정보 획득 및 저장 */

        String username = jwtUtil.getClaim(accessToken, "username");
        String role = jwtUtil.getClaim(accessToken, "role");

        // UserDetails에 유저 정보 저장
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setRole(Role.valueOf(role));
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDto);

        // Spring Security 인증 토큰을 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        // SecurityContextHolder에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }


}
