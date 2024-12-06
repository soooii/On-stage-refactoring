package com.team5.on_stage.global.config.auth;

import com.team5.on_stage.global.config.auth.dto.CustomOAuth2User;
import com.team5.on_stage.global.config.jwt.JwtUtil;
import com.team5.on_stage.global.config.auth.refresh.RefreshService;
import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.team5.on_stage.global.config.auth.cookie.CookieUtil.createCookie;
import static com.team5.on_stage.global.config.jwt.JwtUtil.setErrorResponse;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    public final String REDIRECT = "http://localhost:3000/management";
    private final RefreshService refreshService;


    // Todo: 리다이렉트
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        try {
            CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();

            String username = oauth2User.getUsername();
            String nickname = oauth2User.getNickname();
            String role = oauth2User.getRole().toString();

            String accessToken = jwtUtil.generateAccessToken(username, nickname, role);

            String refreshToken = jwtUtil.generateRefreshToken(username, nickname, role);

            refreshService.saveRefreshToken(refreshToken, username);

            response.addCookie(createCookie("access", accessToken, false));
            response.addCookie(createCookie("refresh", refreshToken, true));
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            setErrorResponse(response, ErrorCode.LOGIN_FAILED);
            throw new GlobalException(ErrorCode.LOGIN_FAILED);
        }

        response.sendRedirect(REDIRECT);
    }
}
