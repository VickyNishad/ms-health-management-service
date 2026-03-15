package com.health.service.impl;

import com.health.dto.MessageResponse;
import com.health.dto.request.ClinicRequest;
import com.health.dto.response.ClinicDetailsDto;
import com.health.entity.Clinic;
import com.health.entity.Doctor;
import com.health.entity.DoctorClinic;
import com.health.models.ApiResponse;
import com.health.repository.ClinicRepository;
import com.health.repository.DoctorClinicRepository;
import com.health.repository.DoctorRepository;
import com.health.service.ClinicService;
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
    private DoctorRepository doctorRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private DoctorClinicRepository doctorClinicRepository;

    /**
     */
    @Override
    public ApiResponse<Clinic> createClinic(ClinicRequest clinic) {

        return ApiExecutionUtils.ApiExecutor.processRequest(null, req ->{},
                () -> {
            Clinic c = getClinic(null, clinic);
            c = clinicRepository.save(c);
            return  c;
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
}
