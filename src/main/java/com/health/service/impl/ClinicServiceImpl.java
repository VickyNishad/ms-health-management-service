package com.health.service.impl;

import com.health.dto.MessageResponse;
import com.health.dto.request.ClinicRequest;
import com.health.dto.response.ClinicDetailsDto;
import com.health.entity.Clinic;
import com.health.entity.Doctor;
import com.health.entity.DoctorClinic;
import com.health.entity.UserRegistration;
import com.health.models.ApiResponse;
import com.health.repository.ClinicRepository;
import com.health.repository.DoctorClinicRepository;
import com.health.repository.DoctorRepository;
import com.health.repository.UserRegistrationRepository;
import com.health.service.ClinicService;
import com.health.service.KycStepService;
import com.health.utility.ApiExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private DoctorClinicRepository doctorClinicRepository;

    @Autowired
    private KycStepService kycStepService;

    /**
     * @param userId
     * @param clinic
     * @return
     */
    @Override
    public ApiResponse<MessageResponse> createClinic(Long userId, ClinicRequest clinic) {

        return ApiExecutionUtils.ApiExecutor.processRequest(null, req ->{},
                () -> {
            Optional<UserRegistration> userRegistration = userRegistrationRepository.findById(userId);
            if(userRegistration.isEmpty()){
                throw new RuntimeException("User not found with userId " + userId);
            }
            UserRegistration userRegistrationEntity = userRegistration.get();

            Clinic c = getClinic(null, clinic);

            c = clinicRepository.save(c);

                    List<Doctor> doctors = doctorRepository.findByUserId(userRegistrationEntity.getId());
                    DoctorClinic doctorClinic = new DoctorClinic();
                    doctorClinic.setClinic(c);
                    doctorClinic.setDoctor(doctors.getFirst());
                    doctorClinic.setCreatedAt(LocalDateTime.now());
                    doctorClinic.setCreatedBy(userRegistrationEntity.getId().toString());
                    doctorClinic.setUpdatedAt(LocalDateTime.now());
                    doctorClinic.setUpdatedBy(userRegistrationEntity.getId().toString());
                    doctorClinic = doctorClinicRepository.save(doctorClinic);

                    kycStepService.addStep(userId,4L);
            return new MessageResponse("Clinic created successfully");
        },ApiResponse::success);
    }

    /**
     * @param userId
     * @param clinicId
     * @param clinic
     * @return
     */
    @Override
    public ApiResponse<MessageResponse> updateClinicById(Long userId, Long clinicId, ClinicRequest clinic) {

        return ApiExecutionUtils.ApiExecutor.processRequest(null, req ->{},
                () -> {
                    Optional<UserRegistration> userRegistration = userRegistrationRepository.findById(userId);
                    if(userRegistration.isEmpty()){
                        throw new RuntimeException("User not found with userId " + userId);
                    }
//                    UserRegistration userRegistrationEntity = userRegistration.get();

                    Optional<Clinic> clinicEntity = clinicRepository.findById(clinicId);
                    if(clinicEntity.isEmpty()){
                        throw new RuntimeException("Clinic not found with clinicId " + clinicId);
                    }

                    Clinic c = getClinic(clinicId, clinic);
                    c.setId(clinicId);

                    c = clinicRepository.save(c);

//                    List<Doctor> doctors = doctorRepository.findByUserId(userRegistrationEntity.getId());
//                    DoctorClinic doctorClinic = new DoctorClinic();
//                    doctorClinic.setClinic(c);
//                    doctorClinic.setDoctor(doctors.getFirst());
//                    doctorClinic.setCreatedAt(LocalDateTime.now());
//                    doctorClinic.setCreatedBy(userRegistrationEntity.getId().toString());
//                    doctorClinic.setUpdatedAt(LocalDateTime.now());
//                    doctorClinic.setUpdatedBy(userRegistrationEntity.getId().toString());
//                    doctorClinic = doctorClinicRepository.save(doctorClinic);
//
//                    kycStepService.addStep(userId,4L);
                    return new MessageResponse("Clinic updated successfully");
                },ApiResponse::success);
    }

    @NonNull
    private static Clinic getClinic(Long clinicId, ClinicRequest clinic) {
        Clinic c = new Clinic();
        c.setClinicName(clinic.getClinicName());
        c.setContactNumber(clinic.getContactNumber());
        c.setFee(clinic.getFee());
        c.setAddress(clinic.getAddress());
        c.setCountry(clinic.getCountry());
        c.setCity(clinic.getCity());
        c.setState(clinic.getState());
        c.setLocality(clinic.getLocality());
        c.setPinCode(clinic.getPinCode());
        return c;
    }

    /**
     * @param userId
     * @param clinicId
     * @return
     */
    @Override
    public ApiResponse<MessageResponse> deleteClinicById(Long userId, Long clinicId) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req ->{},
                ()->{
            Optional<UserRegistration> userRegistration = userRegistrationRepository.findById(userId);
            if(userRegistration.isEmpty()){
                throw new RuntimeException("User not found with userId " + userId);
            }

            List<Doctor> doctors = doctorRepository.findByUserId(userRegistration.get().getId());
            if(doctors.isEmpty()){
                throw new RuntimeException("Doctor not found with userId " + userId);
            }
            List<DoctorClinic> doctorClinics  = doctorClinicRepository.findByDoctor(doctors.getFirst());
            if(doctorClinics.isEmpty()){
                throw new RuntimeException("Doctor clinic not found with userId " + userId);
            }
            for(DoctorClinic doctorClinic : doctorClinics) {
                if(doctorClinic.getClinic().getId().equals(clinicId)){
                    doctorClinicRepository.delete(doctorClinic);
                    break;
                }
            }

            Optional<Clinic> clinicEntity = clinicRepository.findById(clinicId);
            if(clinicEntity.isEmpty()){
                throw new RuntimeException("Clinic not found with clinicId " + clinicId);
            }
            clinicRepository.delete(clinicEntity.get());
            return new MessageResponse("Clinic deleted successfully");
                },
                ApiResponse::success);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public ApiResponse<List<ClinicDetailsDto>> findClinicByDoctorId(Long userId) {

        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req ->{},
                ()->{
            List<ClinicDetailsDto> clinicDetailsDtos = new ArrayList<>();
            List<Doctor> doctors = doctorRepository.findByUserId(userId);

            List<DoctorClinic> doctorClinics = doctorClinicRepository.findByDoctor(doctors.getFirst());
            for (DoctorClinic dc : doctorClinics) {
                ClinicDetailsDto clinicDetailsDto = getClinicDetailsDto(dc);
                clinicDetailsDtos.add(clinicDetailsDto);
            }
            return clinicDetailsDtos;
            },ApiResponse::success);
    }

    @NonNull
    private static ClinicDetailsDto getClinicDetailsDto(DoctorClinic dc) {
        Clinic c = dc.getClinic();
        ClinicDetailsDto clinicDetailsDto = new ClinicDetailsDto();

        clinicDetailsDto.setClinicName(c.getClinicName());
        clinicDetailsDto.setAddress(c.getAddress());
        clinicDetailsDto.setCity(c.getCity());
        clinicDetailsDto.setState(c.getState());
        clinicDetailsDto.setContactNumber(c.getContactNumber());
        clinicDetailsDto.setCity(c.getCity());
        clinicDetailsDto.setFee(c.getFee());
        clinicDetailsDto.setPinCode(c.getPinCode());
        clinicDetailsDto.setLocality(c.getLocality());
        clinicDetailsDto.setCountry(c.getCountry());
        return clinicDetailsDto;
    }
}
