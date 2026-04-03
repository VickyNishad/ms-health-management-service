package com.health.dto.request;

public class UserAuthRequest {

    private String providerLoginId;
    private String password;
    private Long roleId;

    public String getPassword() {
        return password;
    }

    public String getProviderLoginId() {
        return providerLoginId;
    }

    public void setProviderLoginId(String providerLoginId) {
        this.providerLoginId = providerLoginId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
