/**
 * 
 */
package com.health.dto.request;

import com.health.enums.LoginType;

/**
 * 
 */
public class UserRegistrationRequest {

	private String userName;
	private String emailId;
	private String mobileNumber;
	private String empCode;
	private String socialId;
	private LoginType loginType;
	private String password;
	private int roleId;

	public UserRegistrationRequest(String userName, String emailId, String mobileNumber, String empCode,
			String socialId, LoginType loginType, String password, int roleId) {
		super();
		this.userName = userName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.empCode = empCode;
		this.socialId = socialId;
		this.loginType = loginType;
		this.password = password;
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}
