/**
 * 
 */
package com.health.dto.request;

/**
 * 
 */
public class LoginRequest {

	private String providerLoginId;
	private String password;
	private Long roleId;

	
	public LoginRequest(String providerLoginId, String password, Long roleId) {
		super();
		this.providerLoginId = providerLoginId;
		this.password = password;
		this.roleId = roleId;
	}

	public String getProviderLoginId() {
		return providerLoginId;
	}

	public void setProviderLoginId(String providerLoginId) {
		this.providerLoginId = providerLoginId;
	}

	public String getPassword() {
		return password;
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
