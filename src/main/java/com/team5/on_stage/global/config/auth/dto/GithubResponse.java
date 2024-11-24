package com.team5.on_stage.global.config.auth.dto;

import java.util.Map;

public class GithubResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public GithubResponse(final Map<String, Object> attribute) {

        this.attribute = attribute;
    }

    @Override
    public String getProvider() {

        return "github";
    }

    @Override
    public String getProviderId() {

        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {

        if (attribute.get("email") != null) {
            return attribute.get("email").toString();
        }
        return "email@not.provided";
    }

    @Override
    public String getName() {

        if (attribute.get("name") != null) {
            return attribute.get("name").toString();
        }
        return "name@not.provided";
    }
}
