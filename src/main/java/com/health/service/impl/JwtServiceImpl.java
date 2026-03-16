/**
 * 
 */
package com.health.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.health.domain.model.TokenModel;
import com.health.dto.TokenResponse;
import com.health.service.JwtService;
import com.health.utility.Convertors;
import com.health.utility.JwtUtils;

/*
 * 
 */
@Service
public class JwtServiceImpl implements JwtService {

	private final JwtUtils jwtUtils;

	public JwtServiceImpl(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	@Override
	public TokenResponse generateToken(TokenModel model) {
		// TODO Auto-generated method stub
		Map<String, Object> claim = Convertors.convertObjectToMap(model);
		String accessToken = jwtUtils.generateAccessToken(model.getUserName(), claim);
		String refreshToken = jwtUtils.generateRefreshToken(model.getUserName(), claim);
		return new TokenResponse(accessToken, refreshToken, "Bearer", JwtUtils.getExpiry("1").toString(),
				JwtUtils.getExpiry("2").toString());
	}

	@Override
	public TokenResponse refreshToken(String refreshToken) {
		// TODO Auto-generated method stub

//		if (!jwtUtils.validateRefreshToken(refreshToken)) {
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
//	    }
//
//	    // Step 2 — extract username from token
//	    String username = jwtUtils.extractUsername(refreshToken);
//
//	    // Step 3 — (Optional) Verify user still exists and active
//	    Optional<User> userOpt = userRepository.findByUsername(username);
//	    if (userOpt.isEmpty()) {
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
//	    }
//
//	    // Step 4 — generate new access token
//	    Map<String, Object> claims = new HashMap<>();
//	    claims.put("userName", username);
//	    String newAccessToken = jwtUtils.generateAccessToken(username, claims);
//
//	    // Step 5 — return both tokens
//	    return ResponseEntity.ok(Map.of(
//	        "accessToken", newAccessToken,
//	        "refreshToken", refreshToken // you can also issue new refresh token if you want
//	    ));
		return null;
	}

}
