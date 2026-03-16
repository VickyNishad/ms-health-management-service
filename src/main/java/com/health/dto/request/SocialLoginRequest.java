package com.health.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.health.enums.Provider;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialLoginRequest {
    private String socialId;
    private Object data;

    public Object getData() {
        return data;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }
}

