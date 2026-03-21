package com.health.controller;

import com.health.dto.MessageResponse;
import com.health.dto.request.DoctorClinicRequest;
import com.health.dto.request.DoctorPersonalDetailsRequest;
import com.health.dto.response.ClinicDetailsDto;
import com.health.dto.response.DoctorPersonalDetailsDto;
import com.health.dto.response.KycStepResponse;
import com.health.models.ApiResponse;
import com.health.service.ClinicService;
import com.health.service.DoctorService;
import com.health.service.KycStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kyc")
public class DoctorKycController {

    @Autowired
    private KycStepService kycStepService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/{userId}/steps")
    public ResponseEntity<ApiResponse<List<KycStepResponse>>> allSteps(@PathVariable(name = "userId") Long userId) {
        return kycStepService.allSteps(userId);
    }

    @PostMapping("/{userId}/personal")
    public ResponseEntity<ApiResponse<MessageResponse>> personalDetails(@PathVariable Long userId, @RequestBody DoctorPersonalDetailsRequest doctorPersonalDetailsRequest ){
        return doctorService.personalDetails(userId,doctorPersonalDetailsRequest);
    }

    @GetMapping("/{userId}/personal")
    public ApiResponse<DoctorPersonalDetailsDto> getPersonalDetails(@PathVariable Long userId) {
        return doctorService.getPersonalDetails(userId);
    }

    // clinic details
    @PostMapping("/{userId}/clinics")
    public ApiResponse<List<ClinicDetailsDto>> clinics(@PathVariable Long userId, @RequestBody DoctorClinicRequest doctorClinicRequest) {
        return clinicService.createDoctorClinic(userId,doctorClinicRequest);
    }
}
