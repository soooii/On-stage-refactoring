package com.team5.on_stage.global.config.auth.dto;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public KakaoResponse(final Map<String, Object> attribute) {

        this.attribute = attribute;
    }

    @Override
    public String getProvider() {

        return "kakao";
    }

    @Override
    public String getProviderId() {

        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {

        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");

        if (kakaoAccount != null && kakaoAccount.get("email") != null) {

            return kakaoAccount.get("email").toString();
        }

        return null;
    }

    @Override
    public String getName() {

        Map<String, Object> properties = (Map<String, Object>) attribute.get("properties");

        if (properties != null && properties.get("nickname") != null) {

            return properties.get("nickname").toString();
        }

        return null;
    }
}
