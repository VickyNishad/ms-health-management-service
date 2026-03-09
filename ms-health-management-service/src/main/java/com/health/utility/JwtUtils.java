package com.health.utility;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {
	
	private final String SECRET_KEY = "FDJKFGDGFDKHJSGFLDKGFLDSGLDAGLDS563948648URUIT7435843HFJKDGSJ";
	private static final long ACCESS_TOKEN_VALIDITY_MS = 15 * 60 * 1000; // 15 minutes
	private static final long REFRESH_TOKEN_VALIDITY_MS = 7 * 24 * 60 * 60 * 1000; // 7 days

	private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));


	public String generateAccessToken(String username, Map<String, Object> additionalClaims) {
	    Date now = new Date();
	    Date expiry = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_MS);

	    return Jwts.builder()
	            .setSubject(username)
	            .addClaims(additionalClaims)
	            .setIssuedAt(now)
	            .setExpiration(expiry)
	            .signWith(secretKey, SignatureAlgorithm.HS256)
	            .compact();
	}
    
	public String generateRefreshToken(String username, Map<String, Object> additionalClaims) {
	    Date now = new Date();
	    Date expiry = new Date(now.getTime() + REFRESH_TOKEN_VALIDITY_MS);

	    return Jwts.builder()
	            .setSubject(username)
	            .addClaims(additionalClaims)
	            .setIssuedAt(now)
	            .setExpiration(expiry)
	            .signWith(secretKey, SignatureAlgorithm.HS256)
	            .compact();
	}

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            // log if you want
            return false;
        }
    }
    
    public boolean validateRefreshToken(String refreshToken) {
        try {
            
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(refreshToken);

            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("Refresh token expired");
        } catch (JwtException ex) {
            System.out.println("Invalid refresh token");
        }
        return false;
    }


    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
        		.setSigningKey(secretKey)
        		.build()
        		.parseClaimsJws(token)
        		.getBody();
        return claims.getSubject();
    }
    
    public static Date getExpiry(String type) {
	    Date now = new Date();
	   
    	if(type.equalsIgnoreCase("1")) {
    		 Date expiry = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_MS);
    		 return expiry;
    	}else {
    		Date expiry = new Date(now.getTime() + REFRESH_TOKEN_VALIDITY_MS);
    		return expiry;
    	}
    }
}
