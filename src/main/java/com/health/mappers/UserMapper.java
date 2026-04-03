package com.health.mappers;

import com.health.dto.request.CreateUserRequestDTO;
import com.health.dto.response.UserResponseDTO;
import com.health.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequestDTO requestDTO) {
        User user = new User();

        user.setMobileNumber(requestDTO.getMobileNumber());
        user.setPassword(requestDTO.getPassword());
        user.setUserName(requestDTO.getUserName());
        user.setEmpCode(requestDTO.getEmpCode());
        user.setEmailId(requestDTO.getEmailId());
        user.setLoginType(requestDTO.getLoginType().name());
        user.setSocialId(requestDTO.getSocialId());

        return user;
    }

    public UserResponseDTO toDTO(User user) {

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUserId(user.getId());
        userResponseDTO.setUserName(user.getUserName());
        userResponseDTO.setId(user.getId());
        userResponseDTO.setIsRegistered(user.getIsActive());
        userResponseDTO.setIsActive(user.getIsActive());
        userResponseDTO.setLoginType(user.getLoginType());
        userResponseDTO.setRole(user.getRole().getRoleName());
        userResponseDTO.setRoleId(user.getRole().getId());
        return userResponseDTO;

    }
}
