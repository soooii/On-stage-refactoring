package com.team5.on_stage.global.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class TokenUsernameArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        // 메서드 파라미터에 @TokenGetter 어노테이션이 있는지 체크
        return parameter.hasParameterAnnotation(TokenUsername.class);
    }

    @Override
    public String resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // 헤더 추출
        String authorizationHeader = request.getHeader("Authorization");

        // 토큰 추출
        String accessToken = authorizationHeader.split(" ")[1];

        return jwtUtil.getClaim(accessToken, "username");
    }
}
