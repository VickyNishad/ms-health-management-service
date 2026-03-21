/**
 * 
 */
package com.health.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.health.dto.request.PatientRequest;
import com.health.dto.response.PatientDto;
import com.health.repository.PatientRepository;
import com.health.repository.UserRegistrationRepository;
import com.health.utility.ApiExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.health.dto.MessageResponse;
import com.health.entity.Patient;
import com.health.entity.UserRegistration;
import com.health.models.ApiResponse;
import com.health.service.PatientService;

/**
 * 
 */
@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;

	@Autowired
	private PatientRepository  patientRepository;

	@Override
	public ApiResponse<PatientDto> createNewPatient(Long userId, PatientRequest patientRequest) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req ->{},
				()->{
					Optional<UserRegistration> userRegistration = userRegistrationRepository.findById(userId);
					if(userRegistration.isEmpty()) {
						throw new RuntimeException("User not found");
					}
					UserRegistration user = userRegistration.get();
					Patient patient = new Patient();

					patient.setName(patientRequest.getName());
					patient.setGender(patientRequest.getGender());
					patient.setAge(patientRequest.getAge());
 					patient.setDescription(patientRequest.getDescription());
					patient.setEmailId(patientRequest.getEmailId());
					patient.setMobileNumber(patientRequest.getMobileNumber());
					patient.setRelation(patientRequest.getRelation());
					patient.setCreatedAt(LocalDateTime.now());
					patient.setCreatedBy(userId.toString());
					patient.setIsActive(true);
					patient.setUser(user);

					patient = patientRepository.save(patient);
			return new PatientDto(
					patient.getId(),
					patient.getUser().getId(),
					patient.getName(),
					patient.getGender(),
					patient.getAge(),
					patient.getRelation(),
					patient.getMobileNumber(),
					patient.getEmailId(),
					patient.getDescription()
			);
				},
				ApiResponse::success);
	}

	@Override
	public ApiResponse<List<PatientDto>> getPatients(Long userId) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req ->{},
				()->{

					Optional<UserRegistration> userRegistration = userRegistrationRepository.findById(userId);
					if(userRegistration.isEmpty()) {
						throw new RuntimeException("User not found");
					}

				List<Patient> patients = patientRepository.findAllByUserIdAndIsActiveTrue(userId);
					return patients.stream().map(p -> new PatientDto(
							p.getId(),
							p.getUser().getId(),
							p.getName(),
							p.getGender(),
							p.getAge(),
							p.getRelation(),
							p.getMobileNumber(),
							p.getEmailId(),
							p.getDescription()
					)).collect(Collectors.toList());
				},
				ApiResponse::success);
	}

	@Override
	public ApiResponse<PatientDto> getPatient(Long patientId) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req ->{},
				()->{

					Optional<Patient> optionalPatient = patientRepository.findById(patientId);
					if(optionalPatient.isEmpty()) {
						throw new RuntimeException("Patient not found");
					}
					Patient patient = optionalPatient.get();
					patient = patientRepository.save(patient);
					return new PatientDto(
							patient.getId(),
							patient.getUser().getId(),
							patient.getName(),
							patient.getGender(),
							patient.getAge(),
							patient.getRelation(),
							patient.getMobileNumber(),
							patient.getEmailId(),
							patient.getDescription()
					);
				},
				ApiResponse::success);
	}

	@Override
	public ApiResponse<PatientDto> updatePatient(Long userId, Long patientId, PatientRequest patientRequest) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req ->{},
				()->{
					Optional<UserRegistration> userRegistration = userRegistrationRepository.findById(userId);
					if(userRegistration.isEmpty()) {
						throw new RuntimeException("User not found");
					}
					UserRegistration user = userRegistration.get();

					Optional<Patient> optionalPatient = patientRepository.findById(patientId);
					if(optionalPatient.isEmpty()) {
						throw new RuntimeException("Patient not found");
					}
					Patient patient = optionalPatient.get();
					patient.setName(patientRequest.getName());
					patient.setGender(patientRequest.getGender());
					patient.setAge(patientRequest.getAge());
					patient.setDescription(patientRequest.getDescription());
					patient.setEmailId(patientRequest.getEmailId());
					patient.setMobileNumber(patientRequest.getMobileNumber());
					patient.setRelation(patientRequest.getRelation());
					patient.setUpdatedBy(userId.toString());
					patient.setUpdatedAt(LocalDateTime.now());
					patient.setIsActive(true);
					patient.setUser(user);

					patient = patientRepository.save(patient);
					return new PatientDto(
							patient.getId(),
							patient.getUser().getId(),
							patient.getName(),
							patient.getGender(),
							patient.getAge(),
							patient.getRelation(),
							patient.getMobileNumber(),
							patient.getEmailId(),
							patient.getDescription()
					);
				},
				ApiResponse::success);
	}

	@Override
	public ApiResponse<MessageResponse> removePatient(Long userId, Long patientId) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req ->{},
				()->{

					Optional<UserRegistration> userRegistration = userRegistrationRepository.findById(userId);
					if(userRegistration.isEmpty()) {
						throw new RuntimeException("User not found");
					}

					Optional<Patient> optionalPatient = patientRepository.findById(patientId);
					if(optionalPatient.isEmpty()) {
						throw new RuntimeException("Patient not found");
					}
					Patient patient = optionalPatient.get();
					patient.setIsActive(false);
					patient.setUpdatedBy(userId.toString());
					patient.setUpdatedAt(LocalDateTime.now());
					patientRepository.save(patient);
			return new MessageResponse("Patient has been removed");
				},
				ApiResponse::success);
	}
}
