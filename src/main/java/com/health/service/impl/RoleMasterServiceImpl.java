/**
 *
 */
package com.health.service.impl;

import java.util.List;
import java.util.Optional;

import com.health.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.entity.RoleMaster;
import com.health.entity.User;
import com.health.models.ApiResponse;
import com.health.repository.RoleMasterRepository;
import com.health.service.RoleMasterService;
import com.health.utility.ApiExecutionUtils;

/**
 *
 */
@Service
public class RoleMasterServiceImpl implements RoleMasterService {

    @Autowired
    private RoleMasterRepository roleMasterRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse<List<RoleMaster>> findAll() {
        // TODO Auto-generated method stub
        return ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
        }, () -> {
            return roleMasterRepository.findAll();
        }, ApiResponse::success);
    }

	@Override
	public ApiResponse<RoleMaster> findById(Long roleId) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
		}, () -> {
			Optional<RoleMaster> optional = roleMasterRepository.findById(roleId);
			if (optional.isEmpty()) {
				throw new RuntimeException("Role Not Found");
			}
			return optional.get();
		}, ApiResponse::success);
	}

	@Override
	public ApiResponse<RoleMaster> findByRole(String role) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
		}, () -> {
			Optional<RoleMaster> optional = roleMasterRepository.findByRoleName(role);
			if (optional.isEmpty()) {
				throw new RuntimeException("Role Not Found");
			}
			return optional.get();
		}, ApiResponse::success);
	}

	@Override
    public ApiResponse<RoleMaster> findByUserId(Long userId) {
        // TODO Auto-generated method stub

        return ApiExecutionUtils.ApiExecutor.processRequest(userId, req -> {
        }, () -> {
			Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                throw new RuntimeException("User not found. Please create an account to proceed.");
            }
			ApiResponse<RoleMaster> apiResponse = findById(user.get().getRole().getId());
            return apiResponse.getData();
        }, ApiResponse::success);
    }

}
