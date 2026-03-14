/**
 * 
 */
package com.health.dto;

/**
 * 
 */
public class ResetPasswordRequest {
	
	private String providerLoginId;
	private String password;
	private String confirmPassword;
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public ResetPasswordRequest(String providerLoginId, String password, String confirmPassword) {
		super();
		this.providerLoginId = providerLoginId;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}
	
}
