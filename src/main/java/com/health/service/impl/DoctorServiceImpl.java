/**
 * 
 */
package com.health.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.health.dto.request.ClinicRequest;
import com.health.dto.request.DoctorClinicRequest;
import com.health.dto.request.DoctorPersonalDetailsRequest;
import com.health.dto.response.ClinicDetailsDto;
import com.health.entity.*;
import com.health.repository.*;
import com.health.service.*;
import com.health.utility.ApiExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.health.domain.model.DoctorAvailabilityModel;
import com.health.domain.model.DoctorLeaveModel;
import com.health.domain.model.DoctorModel;
import com.health.dto.AvailabilityResponse;
import com.health.dto.DoctorAvailabilityRequest;
import com.health.dto.DoctorAvailabilityUpdateRequest;
import com.health.dto.DoctorLeaveRequest;
import com.health.dto.DoctorLeaveResponse;
import com.health.dto.DoctorProfileRequest;
import com.health.dto.DoctorProfileResponse;
import com.health.dto.DoctorProfileResponse.ClinicResponse;
import com.health.models.ApiResponse;
import com.health.dto.MessageResponse;

/**
 * 
 */
@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;

	@Autowired
	private KycStepService kycStepService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private QualificationMasterRepository qualificationMasterRepository;

	@Autowired
	private  DoctorQualificationRepository doctorQualificationRepository;

	@Autowired
	private SpecializationMasterRepository specializationMasterRepository;

	@Autowired
	private DoctorSpecializationRepository doctorSpecializationRepository;

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private ClinicRepository clinicRepository;

	@Autowired
	private DoctorClinicRepository doctorClinicRepository;

	/**
     */
	@Override
	public ResponseEntity<ApiResponse<MessageResponse>> personalDetails(Long userId, DoctorPersonalDetailsRequest doctorPersonalDetailsRequest) {

		ApiResponse<MessageResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(null,req -> {

		},() -> {
			// check user exist or not
			Optional<UserRegistration> user = userRegistrationRepository.findById(userId);
			if(user.isEmpty()) {
				throw new RuntimeException("User not found. Please create an account to proceed.");
			}

			ApiResponse<UserProfileDetails> apiResponse = userProfileService.getUserProfileDetails(userId);
			if (!apiResponse.isSuccess()) {
				throw new RuntimeException("User profile not found. Please create an account to proceed.");
			}
			UserProfileDetails userProfileDetails = apiResponse.getData();
			userProfileDetails.setAge(doctorPersonalDetailsRequest.getAge());
			userProfileDetails.setGender(doctorPersonalDetailsRequest.getGender());
			userProfileDetails.setEmailId(doctorPersonalDetailsRequest.getEmailId());
			userProfileDetails.setUpdatedAt(LocalDateTime.now());
			userProfileDetails.setUpdatedBy(userId.toString());

			ApiResponse<UserProfileDetails> userProfileDetailsApiResponse = userProfileService.personalDetails(userId,userProfileDetails);
			System.out.println(userProfileDetailsApiResponse.getMessage());

			// insert doctor personal details

			Doctor doctor = new Doctor();
			doctor.setUser(user.get());
			doctor.setNeekName(doctorPersonalDetailsRequest.getNeekName());
			doctor.setName(doctorPersonalDetailsRequest.getName());
			doctor.setRegistrationNumber(doctorPersonalDetailsRequest.getRegistrationNumber());
			doctor.setTotalExperience(doctorPersonalDetailsRequest.getTotalExperience());
			doctor.setIsActive(true);
			doctor.setCreatedAt(LocalDateTime.now());
			doctor.setCreatedBy(userId.toString());
			doctor.setUpdatedAt(LocalDateTime.now());
			doctor.setUpdatedBy(userId.toString());

			List<Doctor> doctors = doctorRepository.findByUserId(userId);
			if(!doctors.isEmpty()) {
				doctor.setId(doctors.getFirst().getId());
			}

			doctor = doctorRepository.save(doctor);

			// insert qualification
			// insert specialization


					if (doctorPersonalDetailsRequest.getSpecializations() != null) {
						// 1. Delete old specialization of doctor
						doctorSpecializationRepository.deleteByDoctorId(doctor.getId());
						// 2. Fetch new specialization record
						List<SpecializationMaster> specializationMasters = specializationMasterRepository
								.findAllById(doctorPersonalDetailsRequest.getSpecializations());

						for (SpecializationMaster s : specializationMasters) {
							DoctorSpecialization ds = new DoctorSpecialization();
							ds.setDoctor(doctor);
							ds.setSpecialization(s);
							ds.setCreatedBy("");
							ds.setIsActive(true);
							doctorSpecializationRepository.save(ds);
						}
					}

					// Step 5: Save DoctorQualifications
					if (doctorPersonalDetailsRequest.getQualifications() != null) {
						// 1. Delete old qualification of doctor
						doctorQualificationRepository.deleteByDoctorId(doctor.getId());

						// 2. Fetch new qualification
						List<QualificationMaster> quals = qualificationMasterRepository
								.findAllById(doctorPersonalDetailsRequest.getQualifications());
						for (QualificationMaster q : quals) {
							DoctorQualification dq = new DoctorQualification();
							dq.setDoctor(doctor);
							dq.setQualification(q);
							dq.setCreatedBy("");
							dq.setIsActive(true);
							doctorQualificationRepository.save(dq);
						}
					}

			return new MessageResponse("Personal Details Successfully Saved");
		},ApiResponse::success);

		return new ResponseEntity<ApiResponse<MessageResponse>>(success,HttpStatus.OK);
	}

	/**
	 */
	@Override
	public ApiResponse<List<ClinicDetailsDto>> createClinic(Long userId, DoctorClinicRequest doctorClinicRequest) {

		return ApiExecutionUtils.ApiExecutor.processRequest(null,req ->{},
				()->{
			// validate user
					Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(userId);
					if (optionalUserRegistration.isEmpty()) {
						throw new RuntimeException("User not found with this userId :"+userId);
					}

					// check doctor profile create or not
					List<Doctor> doctors = doctorRepository.findByUserId(userId);
					if (doctors.isEmpty()) {
						throw new RuntimeException("Doctor not found with this userId :"+userId);
					}
					// check doctor clinic present or not
					List<DoctorClinic> doctorClinics = doctorClinicRepository.findByDoctor(doctors.getFirst());
					// delete old doctor clinic and save new clinic
					if (!doctorClinics.isEmpty()) {
                        doctorClinicRepository.deleteAll(doctorClinics);
					}

					// save clinic as new every time
					List<DoctorClinic> dClinics;
					dClinics = getDoctorClinics(doctors.getFirst(),doctorClinicRequest);
                    doctorClinicRepository.saveAll(dClinics);

					if (doctorClinics.isEmpty()) {
						// save kyc status
						kycStepService.addStep(userId,4L);
					}
			ApiResponse<List<ClinicDetailsDto>> apiResponse = clinicService.findClinicByDoctorId(userId);
			return apiResponse.getData();
		},ApiResponse::success);
	}

	private  List<DoctorClinic> getDoctorClinics(Doctor doctor,DoctorClinicRequest doctorClinicRequest) {
		List<Long> clinicIds = doctorClinicRequest.getClinicIds();
		List<DoctorClinic> doctorClinics = new ArrayList<>();
		for (Long clinicId : clinicIds) {
			DoctorClinic doctorClinic = new DoctorClinic();
			ApiResponse<Clinic> apiResponse = clinicService.findById(clinicId);
			Clinic clinic = apiResponse.getData();
			doctorClinic.setClinic(clinic);
			doctorClinic.setDoctor(doctor);
			doctorClinics.add(doctorClinic);
			doctorClinic.setCreatedAt(LocalDateTime.now());
			doctorClinic.setCreatedBy(doctor.getUser().getId().toString());
			doctorClinic.setUpdatedAt(LocalDateTime.now());
			doctorClinic.setUpdatedBy(doctor.getUser().getId().toString());
		}
		return doctorClinics;
	}

	/**
	 */
	@Override
	public ApiResponse<MessageResponse> deleteClinicById(Long userId, Long clinicId) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req ->{},
                ()->{
            Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(userId);
            if (optionalUserRegistration.isPresent()) {
                throw new RuntimeException("User not found with this userId :"+userId);
            }
            Optional<Clinic> optionalClinic = clinicRepository.findById(clinicId);
            if (optionalClinic.isEmpty()) {
                throw new RuntimeException("Clinic not found with this clinicId :"+clinicId);
            }
            ApiResponse<MessageResponse> apiResponse = clinicService.deleteClinicById(clinicId);
            return apiResponse.getData();
                },ApiResponse::success);
	}

	/**
	 */
	@Override
	public ApiResponse<List<ClinicDetailsDto>> findClinicByDoctorId(Long userId) {
		return clinicService.findClinicByDoctorId(userId);
	}

}
