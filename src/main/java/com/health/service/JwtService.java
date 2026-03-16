/**
 * 
 */
package com.health.service;


import com.health.domain.model.TokenModel;
import com.health.dto.TokenResponse;

/**
 * 
 */
public interface JwtService {

	TokenResponse generateToken(TokenModel model);
	TokenResponse refreshToken(String refreshToken);
}
