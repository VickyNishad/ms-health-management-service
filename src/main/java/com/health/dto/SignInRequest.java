package com.health.dto;

import com.health.enums.Role;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request DTO for user sign-in
 */
@Schema(description = "Sign-in request DTO")
public class SignInRequest {

	private String providerLoginId;
	private String password;

	// Constructor
	public SignInRequest(String providerLoginId, String password) {
		super();
		this.providerLoginId = providerLoginId;
		this.password = password;
	}

	// Getters & Setters
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
