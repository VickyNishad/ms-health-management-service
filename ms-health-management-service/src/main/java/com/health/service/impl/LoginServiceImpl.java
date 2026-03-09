/**
 * 
 */
package com.health.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.common.models.ApiResponse;
import com.common.utility.ApiExecutionUtils;
import com.common.utility.CommonUtils;

import com.health.domain.model.TokenModel;
import com.health.dto.SignInRequest;
import com.health.dto.TokenResponse;
import com.health.entity.RoleMaster;
import com.health.entity.UserRegistration;
import com.health.repository.UserRegistrationRepository;
import com.health.service.JwtService;
import com.health.service.LoginService;

/**
 * 
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRegistrationRepository useRepositoryPort;

	@Autowired
	private JwtService jwtService;


	@Override
	public ResponseEntity<ApiResponse<TokenResponse>> login(SignInRequest signInRequest) {
		// TODO Auto-generated method stub
		ApiResponse<TokenResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(signInRequest, req -> {
			// validate request
		}, () -> {
			Optional<UserRegistration> optionalUser = useRepositoryPort
					.findByProviderLoginId(signInRequest.getProviderLoginId());
			if (optionalUser.isPresent()) {
				RoleMaster roleMaster = optionalUser.get().getRole();
				switch (signInRequest.getRole()) {
				case DOCTOR:
					switch (roleMaster.getRoleName()) {
					case "DOCTOR":
					case "NURSE":
					case "RECEPTIONIST":
						break;
					default:
						throw new RuntimeException("Access denied. Your account is registered as "+ roleMaster.getRoleName()+ ", not "+"DOCTOR/NURSE/RECEPTIONIST"+".");
					}
					break;
				case ADMIN:
					switch (roleMaster.getRoleName()) {
					case "SUPER_ADMIN":
					case "ADMIN":
					case "SALES":
					case "SUPPORT":
						break;
					default:
						throw new RuntimeException("Access denied. Your account is registered as "+ roleMaster.getRoleName()+ ", not "+"SUPER_ADMIN/ADMIN/SALES/SUPPORT"+".");
					}
					break;
				case PATIENT:
					break;
				default:
					throw new RuntimeException("Access denied. Your account is registered as"+ roleMaster.getRoleName()+ ", not "+"PATIENT"+".");
				}

				String encryptPassword = optionalUser.get().getPassword();
				String planPassword = signInRequest.getPassword();
				Boolean match = CommonUtils.matchPassword(planPassword, encryptPassword);
				if (!match) {
					throw new RuntimeException("Password mismatch. Please enter the correct password.");
				}
			} else {
				throw new RuntimeException("User not found. Please create an account to proceed.");
			}

			TokenModel tokenModel = new TokenModel();
			tokenModel.setLoginType(optionalUser.get().getLoginType());
			tokenModel.setRefCode(null);
			tokenModel.setRole(optionalUser.get().getRole().getRoleName());
			tokenModel.setUserId(optionalUser.get().getUserId());
			tokenModel.setUserName(optionalUser.get().getProviderLoginId());

			return jwtService.generateToken(tokenModel);
		}, ApiResponse::success);
		return new ResponseEntity<ApiResponse<TokenResponse>>(success, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<ApiResponse<TokenResponse>> handleSocialSign(SignInRequest signInRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
