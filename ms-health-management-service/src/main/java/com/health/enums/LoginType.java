/**
 * 
 */
package com.health.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 
 */
public enum LoginType {

	MANUAL, GOOGLE, FACEBOOK, APPLE, OTP;

	@JsonCreator
	public static LoginType fromValue(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		return LoginType.valueOf(value.toUpperCase());
	}

	@JsonValue
	public String toValue() {
		return this.name();
	}
}
