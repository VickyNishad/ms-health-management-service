package com.health.service.impl;

import com.health.dto.response.MessageResponse;
import com.health.dto.request.ResetPasswordRequest;
import com.health.dto.request.UpdateUserRequest;
import com.health.dto.response.UserResponseDTO;
import com.health.entity.User;
import com.health.models.ApiResponse;
import com.health.service.ResetPasswordService;
import com.health.service.UserService;
import com.health.utility.ApiExecutionUtils;
import com.health.utility.HealthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

    @Autowired
    private UserService userService;

    @Override
    public ApiResponse<MessageResponse> resetPassword(ResetPasswordRequest request) {
        return ApiExecutionUtils.ApiExecutor.processRequest(request,
                req -> {

                    String password = request.getPassword();
                    String encryptPass = HealthUtils.encryptPassword(password);
                    String cnfPassword = request.getConfirmPassword();
                    boolean match = HealthUtils.matchPassword(cnfPassword, encryptPass);

                    if (!match) {
                        throw new RuntimeException("Passwords do not match. Please try again.");
                    }

                }, () -> {

                    String mobileNumber = request.getProviderLoginId();
                    String password = request.getPassword();
                    String encryptPass = HealthUtils.encryptPassword(password);

                    ApiResponse<User> apiResponse = userService.findByMobileNumber(mobileNumber);
                    if (!apiResponse.isSuccess()) {
                        throw new RuntimeException("We couldn’t find an account associated with this " + request.getProviderLoginId() + ".");
                    }

                    User user = apiResponse.getData();
                    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
                    updateUserRequest.setUserName(user.getUserName());
                    updateUserRequest.setPassword(encryptPass);
                    updateUserRequest.setEmailId(user.getEmailId());
                    ApiResponse<UserResponseDTO> userResponseDTOApiResponse = userService.updateUser(user.getId(), updateUserRequest);

                    if (!userResponseDTOApiResponse.isSuccess()) {
                        throw new RuntimeException(userResponseDTOApiResponse.getMessage());
                    }

                    return new MessageResponse("Your password has been reset successfully.");
                }, ApiResponse::success);
    }
}
