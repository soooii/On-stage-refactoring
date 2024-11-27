package com.team5.on_stage.global.config.auth;

import com.team5.on_stage.global.config.auth.dto.CustomOAuth2User;
import com.team5.on_stage.global.config.jwt.AuthConstants;
import com.team5.on_stage.global.config.jwt.JwtUtil;
import com.team5.on_stage.global.exception.GlobalException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.team5.on_stage.global.config.auth.cookie.CookieUtil.createCookie;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthConstants {

    private final JwtUtil jwtUtil;


    // Todo: 예외처리
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        try {
            CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();

/*            String username = oauth2User.getAttribute("username");
            String role = oauth2User.getAttribute("role");*/

            String username = oauth2User.getUsername();
            String role = oauth2User.getAuthorities().toString();

            String accessToken = jwtUtil.generateToken(ACCESS_TOKEN,
                                                       username,
                                                       role,
                                                       ACCESS_TOKEN_EXPIRED_MS);

            String refreshToken = jwtUtil.generateToken(REFRESH_TOKEN,
                                                        username,
                                                        role,
                                                        REFRESH_TOKEN_EXPIRED_MS);

            jwtUtil.addRefresh(username, refreshToken);

//        response.sendRedirect(request.getContextPath() + "/");
            response.addCookie(createCookie("access", AUTH_TYPE + accessToken));
            response.addCookie(createCookie("refresh", refreshToken));
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            throw new ServletException(e);
        }

        //super.onAuthenticationSuccess(request, response, authentication);
    }
}
