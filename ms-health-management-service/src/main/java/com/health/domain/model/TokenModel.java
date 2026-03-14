/**
 * 
 */
package com.health.domain.model;

/**
 * 
 */
public class TokenModel {
	
	private String userName;
	private String role;
	private String loginType;
	private String refCode;
	private Long patientId;
	private Long doctorId;
	private Long userId;
	
	

	public TokenModel(String userName, String role, String loginType, String refCode) {
		super();
		this.userName = userName;
		this.role = role;
		this.loginType = loginType;
		this.refCode = refCode;
	}

	
	public Long getPatientId() {
		return patientId;
	}


	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}


	public Long getDoctorId() {
		return doctorId;
	}


	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public TokenModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

}
