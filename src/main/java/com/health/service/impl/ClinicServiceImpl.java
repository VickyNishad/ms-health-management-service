package com.health.service.impl;

import com.health.dto.MessageResponse;
import com.health.dto.request.ClinicRequest;
import com.health.dto.request.DoctorClinicRequest;
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
import com.health.service.DoctorService;
import com.health.service.KycStepService;
import com.health.utility.ApiExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    @Lazy
    private DoctorService doctorService;

    @Autowired
    private KycStepService kycStepService;

    /**
     */
    @Override
    public ApiResponse<Clinic> createClinic(Long userId,ClinicRequest clinic) {

        return ApiExecutionUtils.ApiExecutor.processRequest(null, req ->{},
                () -> {
            Clinic c = getClinic(null, clinic);
            c = clinicRepository.save(c);
            List<Long> clinicIds = new ArrayList<>();
            clinicIds.add(c.getId());
                    DoctorClinicRequest doctorClinicRequest = new DoctorClinicRequest();
                    doctorClinicRequest.setClinicIds(clinicIds);
                    ApiResponse<List<ClinicDetailsDto>> apiResponse = createDoctorClinic(userId,doctorClinicRequest);
                    System.out.println(apiResponse.getMessage());
            return  c;
        },ApiResponse::success);
    }

    /**
     */
    @Override
    public ApiResponse<List<ClinicDetailsDto>> createDoctorClinic(Long userId, DoctorClinicRequest doctorClinicRequest) {

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
//                    if (!doctorClinics.isEmpty()) {
//                        doctorClinicRepository.deleteAll(doctorClinics);
//                    }

                    if(doctorClinics.isEmpty()){
                        List<DoctorClinic> dClinics;
                        dClinics = getDoctorClinics(doctors.getFirst(),doctorClinicRequest);
                        doctorClinicRepository.saveAll(dClinics);
                        // save kyc status
                        kycStepService.addStep(userId,4L);
                        ApiResponse<List<ClinicDetailsDto>> apiResponse = findClinicByDoctorId(userId);
                        return apiResponse.getData();
                    }

                    Set<Long> existingClinicIds = doctorClinics.stream()
                            .map(d -> d.getClinic().getId())
                            .collect(Collectors.toSet());

                    List<Long> reqClinicIds = doctorClinicRequest.getClinicIds();

                    if (reqClinicIds == null || reqClinicIds.isEmpty()) {
                        throw new RuntimeException("Enter valid clinic ids");
                    }

                    List<Long> newClinicIds = reqClinicIds.stream()
                            .filter(id -> !existingClinicIds.contains(id))
                            .collect(Collectors.toList());
                    if(!newClinicIds.isEmpty()){
                        doctorClinicRequest.setClinicIds(newClinicIds);
                        List<DoctorClinic> dClinics;
                        dClinics = getDoctorClinics(doctors.getFirst(),doctorClinicRequest);
                        doctorClinicRepository.saveAll(dClinics);
                    }
                    ApiResponse<List<ClinicDetailsDto>> apiResponse = findClinicByDoctorId(userId);
                    return apiResponse.getData();
                },ApiResponse::success);
    }

    /**
     */
    @Override
    public ApiResponse<Clinic> updateClinicById(Long clinicId, ClinicRequest clinic) {

        return ApiExecutionUtils.ApiExecutor.processRequest(null, req ->{},
                () -> {
                    Clinic c = getClinic(clinicId, clinic);
                    c.setId(clinicId);
                    c.setUpdatedAt(LocalDateTime.now());
                    c = clinicRepository.save(c);
                    return c;
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
        c.setCreatedAt(LocalDateTime.now());

        return c;
    }

    /**
     */
    @Override
    public ApiResponse<MessageResponse> deleteClinicById(Long clinicId) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req ->{},
                ()->{
            Optional<Clinic> clinicEntity = clinicRepository.findById(clinicId);
            if(clinicEntity.isEmpty()){
                throw new RuntimeException("Clinic not found with clinicId " + clinicId);
            }
                clinicRepository.delete(clinicEntity.get());
                return new MessageResponse("Clinic deleted successfully");
            }, ApiResponse::success);
    }

    /**
     */
    @Override
    public ApiResponse<MessageResponse> deleteClinicById(Long userId, Long clinicId) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req ->{},
                ()->{
                    Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(userId);
                    if (optionalUserRegistration.isEmpty()) {
                        throw new RuntimeException("User not found with this userId :"+userId);
                    }
                    Optional<Clinic> optionalClinic = clinicRepository.findById(clinicId);
                    if (optionalClinic.isEmpty()) {
                        throw new RuntimeException("Clinic not found with this clinicId :"+clinicId);
                    }

                    ApiResponse<List<ClinicDetailsDto>> listApiResponse = findClinicByDoctorId(userId);
                    if (listApiResponse.getData().isEmpty()) {
                        throw new RuntimeException("Doctor Clinic not found with this clinicId :"+clinicId);
                    }
                    List<ClinicDetailsDto> clinicDetailsDtos = listApiResponse.getData();
                    if (!(clinicDetailsDtos.size() > 1)) {
                        throw new RuntimeException("You cannot delete this clinic, at least 1 clinic is mandatory");
                    }

                    // Delete Doctor Clinic by DoctorClinicId
                    for (ClinicDetailsDto clinicDetailsDto : clinicDetailsDtos) {
                        if (Objects.equals(clinicDetailsDto.getId(), clinicId)) {
                            doctorClinicRepository.deleteById(clinicDetailsDto.getDoctorClinicId());
                        }
                    }

                    ApiResponse<MessageResponse> apiResponse = deleteClinicById(clinicId);
                    return apiResponse.getData();
                },ApiResponse::success);
    }

    /**
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

    /**
     */
    @Override
    public ApiResponse<Clinic> findById(Long clinicId) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,req ->{},()->{
            Optional<Clinic> optionalClinic = clinicRepository.findById(clinicId);
            if(optionalClinic.isEmpty()){
                throw new RuntimeException("Clinic not found with clinicId " + clinicId);
            }
            return optionalClinic.get();
        },ApiResponse::success);
    }

    @NonNull
    private static ClinicDetailsDto getClinicDetailsDto(DoctorClinic dc) {
        Clinic c = dc.getClinic();
        ClinicDetailsDto clinicDetailsDto = new ClinicDetailsDto();
        clinicDetailsDto.setDoctorClinicId(dc.getId());
        clinicDetailsDto.setId(dc.getClinic().getId());
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

    private List<DoctorClinic> getDoctorClinics(Doctor doctor,DoctorClinicRequest doctorClinicRequest) {
        List<Long> clinicIds = doctorClinicRequest.getClinicIds();
        List<DoctorClinic> doctorClinics = new ArrayList<>();
        for (Long clinicId : clinicIds) {
            DoctorClinic doctorClinic = new DoctorClinic();
            ApiResponse<Clinic> apiResponse = findById(clinicId);
            if (!apiResponse.isSuccess()) {
               throw new RuntimeException("Clinic not found with clinicId: " + clinicId);
            }
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
}
