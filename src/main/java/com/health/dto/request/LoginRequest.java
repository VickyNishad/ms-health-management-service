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

	
	public LoginRequest(String providerLoginId, String password) {
		super();
		this.providerLoginId = providerLoginId;
		this.password = password;
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

}
