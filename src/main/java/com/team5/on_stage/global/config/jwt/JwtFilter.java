package com.team5.on_stage.global.config.jwt;

import com.team5.on_stage.global.config.auth.dto.CustomOAuth2User;
import com.team5.on_stage.global.config.auth.dto.UserDto;
import com.team5.on_stage.user.entity.Role;
import io.jsonwebtoken.ExpiredJwtException;
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
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            // 조건에 해당되면 검증에 문제가 있는 것이므로, 메서드를 종료한다.
            return;
        }

        // 3. 'Bearer ' 문자열을 제거한 순수한 토큰을 획득한다.
        String accessToken = authorizationHeader.split(" ")[1];

        // 4. 토큰이 존재하는지 확인한다.
        if (accessToken == null) {

            filterChain.doFilter(request, response);

            return;
        }

        // 5. 토큰 만료 여부 확인. 만료시 오류를 출력하고 다음 필터로 넘기지 않는다.
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            writer.write("{\"error\": \"토큰이 만료되었습니다.\"}");
            writer.flush();
            return;
        }

        // 6. 토큰이 access인지 확인한다. (페이로드에 명시되어 있다.)
        String category = jwtUtil.getType(accessToken);

        // Todo: 예외처리
        if (!category.equals("access")) {

            throw new ServletException("Not access");
        }

        // 토큰에서 email, role 값을 가져온다.
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        // UserDetails에 유저 정보를 담는다.
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setRole(Role.valueOf(role));
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDto);

        // Spring Security 인증 토큰을 생성한다.
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        // SecurityContextHolder에 사용자를 등록한다.
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }


}
