/**
 *
 */
package com.health.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.health.dto.request.CreateUserRequestDTO;
import com.health.dto.request.UpdateUserRequest;
import com.health.dto.response.UserResponseDTO;
import com.health.entity.User;
import com.health.mappers.UserMapper;
import com.health.repository.UserRepository;
import com.health.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.health.dto.response.UserRegisteredResponse;
import com.health.entity.RoleMaster;
import com.health.models.ApiResponse;
import com.health.repository.RoleMasterRepository;
import com.health.utility.ApiExecutionUtils;

/**
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleMasterRepository roleMasterRepository;

    @Autowired
    private KycStepService kycStepService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private JwtService jwtService;

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public ApiResponse<UserRegisteredResponse> isUserRegistered(String providerLoginId) {
        // TODO Auto-generated method stub
        UserRegisteredResponse userRegisteredResponse = new UserRegisteredResponse();
        return ApiExecutionUtils.ApiExecutor.processRequest(providerLoginId, req -> {
        }, () -> {
            Optional<User> user = userRepository.findByMobileNumber(providerLoginId);
            if (user.isPresent()) {
                User userData = user.get();

                userRegisteredResponse.setUserName(userData.getUserName());
                userRegisteredResponse.setIsActive(userData.getIsActive());
                userRegisteredResponse.setIsRegistered(userData.getIsActive());
                userRegisteredResponse.setLoginType(userData.getLoginType());

                RoleMaster roleMaster = userData.getRole();

                userRegisteredResponse.setRole(roleMaster.getRoleName());
                userRegisteredResponse.setRoleId(roleMaster.getId());

            } else {
                throw new RuntimeException("User not found. Please create an account to proceed.");
            }
            return userRegisteredResponse;
        }, ApiResponse::success);
    }

//    @Override
//    public ResponseEntity<ApiResponse<MessageResponse>> resetPassword(ResetPasswordRequest restPasswordRequest) {
//        // TODO Auto-generated method stub
//        ApiResponse<MessageResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(restPasswordRequest,
//                req -> {
//
//                    String password = restPasswordRequest.getPassword();
//                    String encryptPass = HealthUtils.encryptPassword(password);
//                    String cnfPassword = restPasswordRequest.getConfirmPassword();
//                    Boolean match = HealthUtils.matchPassword(cnfPassword, encryptPass);
//
//                    if (!match) {
//                        throw new RuntimeException("Passwords do not match. Please try again.");
//                    }
//
//                }, () -> {
//
//                    String mobileNumber = restPasswordRequest.getProviderLoginId();
//                    String password = restPasswordRequest.getPassword();
//                    String encryptPass = HealthUtils.encryptPassword(password);
//
//                    Optional<User> user = userRepository.findByMobileNumber(mobileNumber);
//                    if (user.isPresent()) {
//                        // return message
//                        User userRegistration = user.get();
//                        userRegistration.setPassword(encryptPass);
//                        userRegistration.setUpdatedAt(LocalDateTime.now());
//                        userRegistration.setUpdatedBy(mobileNumber);
//
//                        userRepository.save(userRegistration);
//                        return new MessageResponse("Your password has been reset successfully.");
//                    } else {
//                        throw new RuntimeException("We couldn’t find an account associated with this " + restPasswordRequest.getProviderLoginId() + ".");
//                    }
//                }, ApiResponse::success);
//        return new ResponseEntity<ApiResponse<MessageResponse>>(success, HttpStatus.OK);
//    }


    @Override
    public ApiResponse<User> findByMobileNumber(String mobileNumber) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req -> {
                },
                () -> {
                    Optional<User> user = userRepository.findByMobileNumber(mobileNumber);
                    return user.orElseThrow(() -> new RuntimeException("User not found"));
                }, ApiResponse::success);
    }

    @Override
    public ApiResponse<User> findByEmailId(String emailId) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req -> {
                },
                () -> {
                    Optional<User> user = userRepository.findByEmailId(emailId);
                    return user.orElseThrow(() -> new RuntimeException("User not found"));
                }, ApiResponse::success);
    }

    @Override
    public ApiResponse<User> findBySocialId(String socialId) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req -> {
                },
                () -> {
                    Optional<User> user = userRepository.findBySocialId(socialId);
                    return user.orElseThrow(() -> new RuntimeException("User not found"));
                }, ApiResponse::success);
    }

    @Override
    public ApiResponse<UserResponseDTO> createUser(CreateUserRequestDTO createUserRequestDTO) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
                },
                () -> {
                    User user = userMapper.toEntity(createUserRequestDTO);
                    user = userRepository.save(user);
                    return userMapper.toDTO(user);
                },
                ApiResponse::success);
    }

	@Override
	public ApiResponse<UserResponseDTO> updateUser(Long id,  UpdateUserRequest updateUserRequest) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
				},
				() -> {
					Optional<User> optionalUser = userRepository.findById(id);
					if (optionalUser.isEmpty()) {
						throw new RuntimeException("User not found");
					}
					User user = optionalUser.get();
					user.setUserName(updateUserRequest.getUserName());
					user.setEmailId(updateUserRequest.getEmailId());
					user.setPassword(updateUserRequest.getPassword());
					user.setUpdatedAt(LocalDateTime.now());
					user.setUpdatedBy(id.toString());
					user = userRepository.save(user);

					return userMapper.toDTO(user);
				},
				ApiResponse::success);
	}

	@Override
	public ApiResponse<UserResponseDTO> findUserById(Long id) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
				},
				() -> {
					Optional<User> user = userRepository.findById(id);
					if (user.isEmpty()) {
						throw new RuntimeException("User not found");
					}
					return userMapper.toDTO(user.get());
				}, ApiResponse::success);
	}
}
