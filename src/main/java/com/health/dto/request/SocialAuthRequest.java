package com.health.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.health.enums.Provider;
import com.health.enums.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialAuthRequest {

    private String socialId;
    private Provider provider;
    private Role role;
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

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
    public Provider getProvider() {
        return provider;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

