/**
 * 
 */
package com.health.dto;



import com.health.enums.Role;

import io.swagger.v3.oas.annotations.Hidden;

/**
 * 
 */
public class PatientSignUpRequest extends UserRegistrationRequest {

	@Hidden
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
