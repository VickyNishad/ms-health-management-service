package com.health.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.health.enums.LoginType;
import com.health.enums.Provider;
import com.health.enums.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialAuthRequest {

    private LoginType loginType;
    private String socialId;
    private Role role;
    private Object data;

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public LoginType getLoginType() {
        return loginType;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

