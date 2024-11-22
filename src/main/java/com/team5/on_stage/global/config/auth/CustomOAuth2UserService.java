package com.team5.on_stage.global.config.auth;

import com.team5.on_stage.global.config.auth.dto.*;
import com.team5.on_stage.user.entity.Role;
import com.team5.on_stage.user.entity.TempUser;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.TempUserRepository;
import com.team5.on_stage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final TempUserRepository tempUserRepository;


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

        TempUser existData = tempUserRepository.findByUsername(username);

        // Todo: 회원가입 기능 고려하여 수정하기 + dto 사용
        if (existData == null) {
            // 사용자 정보가 없는 경우, DB에 정보를 새로이 저장한다.
            System.out.println("정보가 없는 경우");
            TempUser tempUser = new TempUser();
            tempUser.setUsername(username);
            tempUser.setEmail(oAuth2Response.getEmail());
            tempUser.setName(oAuth2Response.getName());
            tempUser.setRole(Role.valueOf("ROLE_USER"));

            tempUserRepository.save(tempUser);

            UserDto userDto = new UserDto();
            userDto.setUsername(username);
            userDto.setName(oAuth2Response.getName());
            userDto.setRole("ROLE_USER");

            return new CustomOAuth2User(userDto);
        }
        else {
            // DB에 사용자 정보가 있는 경우, 정보를 업데이트한다.
            System.out.println("정보가 있는 경우");
            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            tempUserRepository.save(existData);

            UserDto userDto = new UserDto();
            userDto.setUsername(existData.getUsername());
            userDto.setName(oAuth2Response.getName());
            userDto.setRole(existData.getRole().toString());

            return new CustomOAuth2User(userDto);
        }




    }
}
