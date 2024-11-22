package com.team5.on_stage.global.config.auth.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final UserDto userDto;


    // Todo: 도메인에 따라 Attribute의 형태가 다르다. 그래서 획일화하기가 어려움.
    @Override
    public Map<String, Object> getAttributes() {


        return null;
    }

    // Todo: 필수 구현인데.. Role을 사용하지 않는다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

                return userDto.getRole();
            }
        });
        return List.of();
    }

    @Override
    public String getName() {

        return userDto.getName();
    }

    public String getUsername() {

        return userDto.getUsername();
    }
}
