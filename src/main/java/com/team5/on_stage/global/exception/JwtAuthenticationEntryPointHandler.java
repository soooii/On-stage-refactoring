package com.team5.on_stage.global.exception;

import com.team5.on_stage.global.config.jwt.JwtUtil;
import com.team5.on_stage.global.constants.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.team5.on_stage.global.config.jwt.JwtUtil.setErrorResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final JwtUtil jwtUtil;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("유효하지 않은 인증입니다.");
            ErrorCode errorCode = ErrorCode.INVALID_TOKEN;
            setErrorResponse(response, errorCode);

            return;
        }

        String accessToken = authorizationHeader.split(" ")[1];

        if (jwtUtil.isExpired(accessToken)) {
            log.error("토큰이 만료되었습니다.");
            ErrorCode errorCode = ErrorCode.TOKEN_EXPIRED;
            setErrorResponse(response, errorCode);
        }
    }
}
