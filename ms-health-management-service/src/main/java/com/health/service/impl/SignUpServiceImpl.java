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
import com.common.utility.Validators;

import com.health.domain.model.TokenModel;
import com.health.domain.model.UserRegistrationModel;
import com.health.dto.DoctorSignUpRequest;
import com.health.dto.PatientSignUpRequest;
import com.health.dto.TokenResponse;
import com.health.entity.RoleMaster;
import com.health.entity.UserRegistration;
import com.health.repository.DoctorRepository;
import com.health.repository.PatientRepository;
import com.health.repository.RoleMasterRepository;
import com.health.repository.UserRegistrationRepository;
import com.health.service.JwtService;
import com.health.service.SignUpService;

/**
 * 
 */
@Service
public class SignUpServiceImpl implements SignUpService {

	@Autowired
	private UserRegistrationRepository useRepositoryPort;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private RoleMasterRepository rolMasterRepositoryPort;

	@Autowired
	private JwtService jwtService;

	@Override
	public ResponseEntity<ApiResponse<TokenResponse>> patientSignUp(PatientSignUpRequest signUpRequest) {
		// TODO Auto-generated method stub

		// check user Exists or not

		ApiResponse<TokenResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(signUpRequest, req -> {
			/**
			 * Validate request here all parameters.
			 */
			Validators.validateRequestedField("mobileNumber", req.getProviderLoginId(), true, 10, 10);
			Validators.validateRequestedField("password", req.getPassword(), 4, 16);
			Validators.validateRequestedField("name", req.getFullName());
			Validators.validateRequestedField("loginType", req.getLoginType().name());
		}, () -> {
			Optional<UserRegistration> user = useRepositoryPort
					.findByProviderLoginId(signUpRequest.getProviderLoginId());
			if (user.isPresent()) {
				// return message
				throw new RuntimeException("You already have an account. Please log in to continue.");
			}

			Optional<RoleMaster> role = rolMasterRepositoryPort.findByRoleName(signUpRequest.getRole().toValue());
			if (!role.isPresent()) {
				// return message
				throw new RuntimeException("Role not found.");
			}

			UserRegistrationModel model = new UserRegistrationModel();

			model.setContactNumber(null);
			model.setEmailId(null);
			model.setFullName(signUpRequest.getFullName());
			model.setLoginType(signUpRequest.getLoginType().toValue());
			model.setPassword(CommonUtils.encryptPassword(signUpRequest.getPassword()));
			model.getProfilePicture();
			model.setProviderLoginId(signUpRequest.getProviderLoginId());
			model.setRole(role.get());
			model.setSocialId(null);

			UserRegistration userRegistration = useRepositoryPort.save(model);

			//
//			PatientModel patientModel = new PatientModel();
//			patientModel.setAge(null);
//			patientModel.setContactNumber(null);
//			patientModel.setGender(null);
//			patientModel.setPatientId(null);
//			patientModel.setUser(userRegistration);
//
//			Patient patient = patientRepository.save(patientModel);

			TokenModel tokenModel = new TokenModel();
			tokenModel.setUserId(userRegistration.getUserId());
//			tokenModel.setPatientId(patient.getPatientId());
			tokenModel.setLoginType(userRegistration.getLoginType());
			tokenModel.setRefCode(null);
			tokenModel.setRole(role.get().getRoleName());
			tokenModel.setUserName(userRegistration.getProviderLoginId());

			return jwtService.generateToken(tokenModel);
		}, ApiResponse::success);
		return new ResponseEntity<ApiResponse<TokenResponse>>(success, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse<TokenResponse>> doctorSignUp(DoctorSignUpRequest signUpRequest) {
		// TODO Auto-generated method stub
		// check user Exists or not

		ApiResponse<TokenResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(null, null, () -> {
			Optional<UserRegistration> user = useRepositoryPort
					.findByProviderLoginId(signUpRequest.getProviderLoginId());
			if (user.isPresent()) {
				throw new RuntimeException("You already have an account. Please log in to continue.");
			}

			Optional<RoleMaster> role = rolMasterRepositoryPort.findByRoleName(signUpRequest.getRole().toValue());
			if (!role.isPresent()) {				
				throw new RuntimeException("Role not found.");
			}

			UserRegistrationModel model = new UserRegistrationModel();

			model.setContactNumber(signUpRequest.getProviderLoginId());
			model.setFullName(signUpRequest.getFullName());
			model.setLoginType(signUpRequest.getLoginType().toValue());
			model.setPassword(CommonUtils.encryptPassword(signUpRequest.getPassword()));
			model.setProviderLoginId(signUpRequest.getProviderLoginId());
			model.setRole(role.get());

			UserRegistration userRegistration = useRepositoryPort.save(model);

//			DoctorModel doctorModel = new DoctorModel();
//
//			doctorModel.setUser(userRegistration);
//			doctorModel.setCreatedBy(null);
//			doctorModel.setUpdatedBy(null);
//
//			Doctor doctor = doctorRepository.save(doctorModel);
			System.out.println(userRegistration.getUserId());
			TokenModel tokenModel = new TokenModel();
			tokenModel.setUserId(userRegistration.getUserId());
//			tokenModel.setDoctorId(doctor.getDoctorId());
			tokenModel.setLoginType(userRegistration.getLoginType());
			tokenModel.setRefCode(null);
			tokenModel.setRole(role.get().getRoleName());
			tokenModel.setUserName(userRegistration.getProviderLoginId());

			return jwtService.generateToken(tokenModel);
		}, ApiResponse::success);

		return new ResponseEntity<ApiResponse<TokenResponse>>(success, HttpStatus.OK);
	}

}
