package com.team5.on_stage.global.config.auth;

import com.team5.on_stage.global.config.auth.dto.*;
import com.team5.on_stage.user.entity.*;
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

        if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("github")) {

            oAuth2Response = new GithubResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }
        else {
            return null;
        }

        // 리소스 서버에서 발급 받은 정보로 사용자를 특정하는 아이디 값을 만든다.
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        User existUser = userRepository.findByUsername(username);

        if (existUser == null) {
            // 사용자 정보가 없는 경우, DB에 정보를 새로이 저장한다.
            User newUser = User.builder()
                    .nickname(getTempNickname())
                    .username(username)
                    .email(oAuth2Response.getEmail())
                    .emailDomain(EmailDomain.valueOf(extractDomain(oAuth2Response.getEmail())))
                    .name(oAuth2Response.getName())
                    .verified(Verified.UNVERIFIED)
                    .role(Role.ROLE_USER)
                    .image("Default Image") // Todo: 기본 이미지 설정
                    .build();

            userRepository.save(newUser);

            UserDto userDto = new UserDto();
            userDto.setUsername(username);
            userDto.setName(oAuth2Response.getName());
            userDto.setRole(Role.ROLE_USER);

            return new CustomOAuth2User(userDto);
        }
        else {
            // DB에 사용자 정보가 있는 경우, 정보를 업데이트한다.
            existUser.updateOAuthUser(oAuth2User.getName(), oAuth2Response.getEmail());

            userRepository.save(existUser);

            UserDto userDto = new UserDto();
            userDto.setUsername(existUser.getUsername());
            userDto.setName(oAuth2Response.getName());
            userDto.setRole(existUser.getRole());

            return new CustomOAuth2User(userDto);
        }


    }

    // Todo: username에서 추출하도록 수정할 것
    public static String extractDomain(String email) {

        return email.substring(email.indexOf("@") + 1, email.lastIndexOf(".")).toUpperCase();
    }

    public String getTempNickname() {

        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String tempNickname = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            tempNickname += charSet[idx];
        }
        return tempNickname;
    }
}
