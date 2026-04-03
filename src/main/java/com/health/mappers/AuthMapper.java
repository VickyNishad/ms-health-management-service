package com.health.mappers;

import com.health.dto.request.AuthRequest;
import com.health.dto.response.AuthResponse;
import com.health.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthResponse toAuthResponse(User user) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUserId(user.getId());
        authResponse.setUserName(user.getUserName());
        authResponse.setActive(user.getIsActive());
        authResponse.setRole(user.getRole().getRoleName());
        authResponse.setRoleId(user.getRole().getId());
        authResponse.setUserRegistered(user.getIsActive());
        return authResponse;
    }
}
