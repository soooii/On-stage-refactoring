package com.team5.on_stage.global.config.auth;

import com.team5.on_stage.global.config.auth.dto.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    // 사용자 정보를 확인하기 위한 메서드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        // 구글은 JSON 응답의 body에 id를 담는다.
        if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        // 네이버는 JSON 응답 body의 response 내부에 id를 담는다.
        else if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else {
            return null;
        }

        // 리소스 서버에서 발급 받은 정보로 사용자를 특정하는 아이디 값을 만든다.
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setName(oAuth2Response.getName());

        return new CustomOAuth2User(userDto);
    }
}
