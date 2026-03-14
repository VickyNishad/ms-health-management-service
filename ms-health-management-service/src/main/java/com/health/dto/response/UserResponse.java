/**
 * 
 */
package com.health.dto.response;

import com.health.dto.TokenResponse;

/**
 * 
 */
public class UserResponse {

	private Long id;
	private Long userId;
	private String userName;
	private Boolean isRegistered;
	private String role;
	private Long roleId;
	private Boolean isActive;
	private String loginType;
	private TokenResponse tokenResponse;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Boolean getIsRegistered() {
		return isRegistered;
	}

	public void setIsRegistered(Boolean isRegistered) {
		this.isRegistered = isRegistered;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public TokenResponse getTokenResponse() {
		return tokenResponse;
	}

	public void setTokenResponse(TokenResponse tokenResponse) {
		this.tokenResponse = tokenResponse;
	}

}
