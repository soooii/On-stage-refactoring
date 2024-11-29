package com.team5.on_stage.global.config.auth;

import com.team5.on_stage.global.config.auth.dto.CustomOAuth2User;
import com.team5.on_stage.global.config.jwt.JwtUtil;
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
import static com.team5.on_stage.global.config.jwt.AuthConstants.*;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;


    // Todo: 예외처리
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        try {
            CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();

            String username = oauth2User.getUsername();
            String role = oauth2User.getAuthorities().toString();

            String accessToken = jwtUtil.generateToken(TYPE_ACCESS,
                                                       username,
                                                       role,
                                                       ACCESS_TOKEN_EXPIRED_MS);

            String refreshToken = jwtUtil.generateToken(TYPE_REFRESH,
                                                        username,
                                                        role,
                                                        REFRESH_TOKEN_EXPIRED_MS);

            jwtUtil.addRefresh(username, refreshToken);

//            ObjectMapper mapper = new ObjectMapper();
//            String cookieTokenValue = mapper.writeValueAsString(Map.of(
//                    "refresh", refreshToken,
//                    "access", AUTH_TYPE + accessToken)
//            );
//
//            String encodedCookieValue = URLEncoder.encode(cookieTokenValue, StandardCharsets.UTF_8);
//
//            response.addCookie(createCookie("token", encodedCookieValue));

            response.addCookie(createCookie("access", accessToken, false));
            response.addCookie(createCookie("refresh", refreshToken, true));
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            throw new ServletException(e);
        }

        //super.onAuthenticationSuccess(request, response, authentication);
        response.sendRedirect(FRONT_DOMAIN);
    }
}
