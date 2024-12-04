package com.team5.on_stage.global.config.auth;

import com.team5.on_stage.global.config.auth.dto.*;
import com.team5.on_stage.socialLink.service.SocialLinkService;
import com.team5.on_stage.theme.service.ThemeService;
import com.team5.on_stage.user.entity.*;
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
    private final ThemeService themeService;
    private final SocialLinkService socialLinkService;


    // 사용자 정보를 확인하기 위한 메서드
    // Todo: 유저 저장 로직을 SuccessHandler에 넣을지 고민
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        switch(registrationId) {
            case "google" -> oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
            case "naver" -> oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
            case "github" -> oAuth2Response = new GithubResponse(oAuth2User.getAttributes());
            case "kakao" -> oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
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
                    .OAuth2Domain(OAuth2Domain.valueOf(registrationId.toUpperCase()))
                    .name(oAuth2Response.getName())
                    .build();

            userRepository.save(newUser);

            themeService.createTheme(username);
            socialLinkService.createSocial(username);

            UserDto userDto = new UserDto();
            userDto.setUsername(username);
            userDto.setName(oAuth2Response.getName());
            userDto.setRole(Role.ROLE_USER);
            userDto.setNickname(newUser.getNickname());

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
            userDto.setNickname(existUser.getNickname());

            return new CustomOAuth2User(userDto);
        }


    }


    // 임시 닉네임 생성
    public String getTempNickname() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z' };

        String tempNickname;
        do {
            StringBuilder nicknameBuilder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                int idx = (int) (charSet.length * Math.random());
                nicknameBuilder.append(charSet[idx]);
            }
            tempNickname = nicknameBuilder.toString();
        } while (userRepository.existsByNickname(tempNickname));

        return tempNickname;
    }
}
