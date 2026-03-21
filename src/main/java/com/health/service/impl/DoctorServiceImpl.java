/**
 * 
 */
package com.health.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.health.dto.request.AvailabilityRequest;
import com.health.dto.request.DoctorPersonalDetailsRequest;
import com.health.dto.response.DoctorClinicAvailabilityDto;
import com.health.dto.response.DoctorPersonalDetailsDto;
import com.health.dto.response.MasterSummary;
import com.health.entity.*;
import com.health.repository.*;
import com.health.service.*;
import com.health.utility.ApiExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;


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
	private UserProfileService userProfileService;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private DoctorClinicRepository doctorClinicRepository;

	@Autowired
	private KycStepService kycStepService;

	@Autowired
	private QualificationMasterRepository qualificationMasterRepository;

	@Autowired
	private  DoctorQualificationRepository doctorQualificationRepository;

	@Autowired
	private SpecializationMasterRepository specializationMasterRepository;

	@Autowired
	private DoctorSpecializationRepository doctorSpecializationRepository;

	@Autowired
	private DoctorClinicAvailabilityRepository doctorClinicAvailabilityRepository;


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

		return new ResponseEntity<>(success, HttpStatus.OK);
	}

	@Override
	public ApiResponse<DoctorPersonalDetailsDto> getPersonalDetails(Long userId) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req ->{

				},
				()->{
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

					List<Doctor> doctors = doctorRepository.findByUserId(userId);
                    return getDoctorPersonalDetailsDto(doctors, userProfileDetails);
				},
				ApiResponse::success);
	}

	@Override
	public ApiResponse<MessageResponse> createAvailability(Long userId,Long clinicId,AvailabilityRequest request) {
	return ApiExecutionUtils.ApiExecutor.processRequest(null,
			req ->{},
			()->{
				List<Doctor> doctors = doctorRepository.findByUserId(userId);
				if (doctors.isEmpty()) {
					throw new RuntimeException("Doctor not found. Please create an account to proceed.");
				}

				Doctor doctor = doctors.getFirst();
				List<DoctorClinic> doctorClinics = doctorClinicRepository.findByDoctor(doctor);
				if (doctorClinics.isEmpty()) {
					throw new RuntimeException("Doctor Clinics not found. Please create an account to proceed.");
				}
				Long doctorClinicId = 0L;
				for (DoctorClinic clinic : doctorClinics) {
					if(clinic.getClinic().getId().equals(clinicId)) {
						doctorClinicId = clinic.getId();
					}
				}
				validateAvailability(doctorClinicId,request.getDayOfWeek(),request.getStartTime(),request.getEndTime());
				List<DoctorClinicAvailability> availabilities =
						doctorClinicAvailabilityRepository.findByDoctorClinicId(doctorClinicId);

				for (DoctorClinic doctorClinic : doctorClinics) {
					if (doctorClinic.getClinic().getId().equals(clinicId)) {
						DoctorClinicAvailability availability = new DoctorClinicAvailability();
						availability.setDoctorClinic(doctorClinic);
						availability.setDayOfWeek(request.getDayOfWeek());
						availability.setStartTime(request.getStartTime());
						availability.setEndTime(request.getEndTime());
						availability.setIsActive(true);
						doctorClinicAvailabilityRepository.save(availability);
					}
				}
				if (availabilities.isEmpty()){
					kycStepService.addStep(userId,5L);
				}
				return new MessageResponse("Clinic Availability Created Successfully");
			},
			ApiResponse::success);
	}

	@Override
	public ApiResponse<List<DoctorClinicAvailabilityDto>> getAvailabilityClinicId(Long userId, Long clinicId) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req ->{},
				()->{
					List<Doctor> doctors = doctorRepository.findByUserId(userId);
					if (doctors.isEmpty()) {
						throw new RuntimeException("Doctor not found. Please create an account to proceed.");
					}

					Doctor doctor = doctors.getFirst();
					List<DoctorClinic> doctorClinics = doctorClinicRepository.findByDoctor(doctor);
					if (doctorClinics.isEmpty()) {
						throw new RuntimeException("Doctor Clinics not found. Please create an account to proceed.");
					}

					Long doctorClinicId = 0L;
					for (DoctorClinic clinic : doctorClinics) {
						if(clinic.getClinic().getId().equals(clinicId)) {
							doctorClinicId = clinic.getId();
						}
					}
					List<DoctorClinicAvailability> availabilities = doctorClinicAvailabilityRepository.findByDoctorClinicId(doctorClinicId);
					return availabilities.stream().map(a -> new DoctorClinicAvailabilityDto(
							a.getId(),
							a.getDoctorClinic().getClinic().getId(),
							a.getDoctorClinic().getDoctor().getId(),
							a.getDayOfWeek(),
							a.getStartTime(),
							a.getEndTime()

					) ).collect(Collectors.toList());

				},
				ApiResponse::success);
	}

	@Override
	public ApiResponse<List<DoctorClinicAvailabilityDto>> getAvailability(Long userId) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req ->{},
				()->{
					List<Doctor> doctors = doctorRepository.findByUserId(userId);
					if (doctors.isEmpty()) {
						throw new RuntimeException("Doctor not found. Please create an account to proceed.");
					}

					Doctor doctor = doctors.getFirst();
					List<DoctorClinic> doctorClinics = doctorClinicRepository.findByDoctor(doctor);
					if (doctorClinics.isEmpty()) {
						throw new RuntimeException("Doctor Clinics not found. Please create an account to proceed.");
					}

					List<DoctorClinicAvailabilityDto> availabilityDtoList = new ArrayList<>();

					for (DoctorClinic clinic : doctorClinics) {

						Long doctorClinicId = clinic.getId();

						List<DoctorClinicAvailability> availabilities =
								doctorClinicAvailabilityRepository.findByDoctorClinicId(doctorClinicId);

						List<DoctorClinicAvailabilityDto> dtoList = availabilities.stream()
								.map(a -> new DoctorClinicAvailabilityDto(
										a.getId(),
										a.getDoctorClinic().getClinic().getId(),
										a.getDoctorClinic().getDoctor().getId(),
										a.getDayOfWeek(),
										a.getStartTime(),
										a.getEndTime()
								))
								.toList();

						availabilityDtoList.addAll(dtoList); // 🔥 important

					}
					return availabilityDtoList;
				},
				ApiResponse::success);
	}

	public void validateAvailability(Long doctorClinicId, DayOfWeek day,
	                                 LocalTime start, LocalTime end) {

		List<DoctorClinicAvailability> list =
				doctorClinicAvailabilityRepository.findByDoctorClinicIdAndDayOfWeekAndIsActiveTrue(doctorClinicId, day);

		for (DoctorClinicAvailability existing : list) {

			boolean isOverlapping =
					start.isBefore(existing.getEndTime()) &&
							end.isAfter(existing.getStartTime());

			if (isOverlapping) {
				throw new RuntimeException("Time slot overlaps with existing availability");
			}
		}
	}

	@NonNull
	private DoctorPersonalDetailsDto getDoctorPersonalDetailsDto(List<Doctor> doctors, UserProfileDetails userProfileDetails) {
		if (doctors.isEmpty()) {
			throw new RuntimeException("Doctor not found. Please create an account to proceed.");
		}

		Doctor doctor = doctors.getFirst();

		DoctorPersonalDetailsDto dto = new DoctorPersonalDetailsDto();
		dto.setDoctorId(doctor.getId());
		dto.setName(doctor.getName());
		dto.setNeekName(doctor.getNeekName());
		dto.setMobileNumber(userProfileDetails.getMobileNumber());
		dto.setEmailId(userProfileDetails.getEmailId());
		dto.setAge(userProfileDetails.getAge());
		dto.setGender(userProfileDetails.getGender());
		dto.setRegistrationNumber(doctor.getRegistrationNumber());
		dto.setTotalExperience(doctor.getTotalExperience());
		dto.setQualifications(getQualifications(doctor));
		dto.setSpecializations(getSpecializations(doctor));
		return dto;
	}

	private List<MasterSummary> getQualifications(Doctor doctor){
		List<DoctorQualification> qualifications = doctorQualificationRepository.findByDoctorId(doctor.getId());

		return qualifications.stream()
				.map(d -> new MasterSummary(
						d.getId(),
						d.getQualification().getQualificationName(),
						d.getQualification().getDescription()
				))
				.collect(Collectors.toList());
	}

	private List<MasterSummary> getSpecializations(Doctor doctor){
		List<DoctorSpecialization> specializations = doctorSpecializationRepository.findByDoctorId(doctor.getId());

		return specializations.stream()
				.map(d -> new MasterSummary(
						d.getId(),
						d.getSpecialization().getSpecializationName(),
						d.getSpecialization().getDescription()
				))
				.collect(Collectors.toList());
	}

}
