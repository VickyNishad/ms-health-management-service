package com.health.dto.response;

import com.health.dto.TokenResponse;

public class AuthResponse {

    private Long userId;
    private String userName;
    private Boolean isUserRegistered;
    private String role;
    private Long roleId;
    private Boolean isActive;
    private String loginType;
    private TokenResponse token;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getUserRegistered() {
        return isUserRegistered;
    }

    public void setUserRegistered(Boolean userRegistered) {
        isUserRegistered = userRegistered;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public TokenResponse getToken() {
        return token;
    }

    public void setToken(TokenResponse token) {
        this.token = token;
    }
}
