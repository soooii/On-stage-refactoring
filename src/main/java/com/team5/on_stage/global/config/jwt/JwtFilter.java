package com.team5.on_stage.global.config.jwt;

import com.team5.on_stage.global.config.auth.AuthTokenImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProviderImpl tokenProvider;
    private final JwtTokenResolver tokenResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = tokenResolver.resolveToken(request);

        if (token.isPresent()) {
            AuthTokenImpl jwtToken = tokenProvider.convertAuthToken(token.get().split(" ")[1]);
            String jti = jwtToken.getDate().getId();

//            if (!blacklistService.isBlacklisted(jti) && jwtToken.validate()) {
//                Authentication authentication = tokenProvider.getAuthentication(jwtToken);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }

        }
        filterChain.doFilter(request, response);
    }


}
