
package com.health.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.exception.AuthenticationFailedException;
import com.health.models.ApiResponse;
import com.health.utility.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    // List of URLs to skip JWT validation
    private static final String[] WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/api-docs/**",
            "/api/v1/auth/**",
            "/medicque/menu/**",
            "/api/v1/master/**",
            "/api/v1/otp/**",
            "/api/v1/doctor/**",
            "/api/v1/clinic/**",
            "/api/v1/search/**",
            "/api/v1/kyc/**",
            "/api/v1/patient/**",
            "/api/v1/slot/**"
    };

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

		try {
			String path = request.getRequestURI();
			for (String pattern : WHITELIST) {
				if (pathMatcher.match(pattern, path)) {
					filterChain.doFilter(request, response);
					return;
				}
			}
            // existing JWT validation logic...
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new AuthenticationFailedException("Missing or malformed Authorization header");
            }

            String token = authHeader.substring(7);
            String username = jwtUtils.extractUsername(token);
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                new User(username, "", Collections.emptyList()),
                                null,
                                Collections.emptyList()
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (AuthenticationFailedException e) {
            unauthorizedResponse(response, e.getMessage(), request.getRequestURI());
        } catch (ExpiredJwtException e) {
            unauthorizedResponse(response, "Token expired", request.getRequestURI());
        } catch (MalformedJwtException e) {
            unauthorizedResponse(response, "Invalid JWT token", request.getRequestURI());
        }
    }

    private void unauthorizedResponse(HttpServletResponse response, String message, String path) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        new ObjectMapper().writeValue(response.getOutputStream(), ApiResponse.error(message));
    }
}

