/**
 * 
 */
package com.health.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.health.enums.LoginType;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegistrationRequest {

	@Schema(description = "name of the user", name = "userName", type = "string", example = "Vatsal")
	private String userName;
	
	@Schema(description = "providerLoginId of the user", name = "providerLoginId", type = "string", example = "9876543210 / E001")
	private String providerLoginId;
	
	@Schema(description = "password of the user", name = "password", type = "string", example = "Exmple@123 / 1234 / 0000")
	private String password;
	
	@Schema(description = "Select loginType", name = "loginType", type = "string", example = "MANUAL/SOCIAL")
	private LoginType loginType;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public UserRegistrationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
