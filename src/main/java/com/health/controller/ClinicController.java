package com.health.controller;

import com.health.dto.MessageResponse;
import com.health.dto.request.ClinicRequest;
import com.health.dto.response.ClinicDetailsDto;
import com.health.entity.Clinic;
import com.health.models.ApiResponse;
import com.health.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clinic")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @PostMapping("/{userId}/create")
    public ApiResponse<Clinic> clinics(@PathVariable Long userId, @RequestBody ClinicRequest clinicRequest) {
        return clinicService.createClinic(userId,clinicRequest);
    }

    @PostMapping("/{clinicId}/update")
    public ApiResponse<Clinic> updateClinics(@PathVariable Long clinicId, @RequestBody ClinicRequest clinicRequest) {
        return clinicService.updateClinicById(clinicId,clinicRequest);
    }

    @GetMapping("/{userId}/{clinicId}/delete")
    public ApiResponse<MessageResponse> deleteClinic(@PathVariable Long userId, @PathVariable Long clinicId){
        return clinicService.deleteClinicById(userId,clinicId);
    }

    @GetMapping("/{userId}/clinics")
    public ApiResponse<List<ClinicDetailsDto>> getClinics(@PathVariable Long userId){
        return clinicService.findClinicByDoctorId(userId);
    }
}
