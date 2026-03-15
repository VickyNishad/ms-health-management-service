package com.health.controller;

import com.health.dto.request.ClinicRequest;
import com.health.entity.Clinic;
import com.health.models.ApiResponse;
import com.health.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clinic")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @PostMapping()
    public ApiResponse<Clinic> clinics(@RequestBody ClinicRequest clinicRequest) {
        return clinicService.createClinic(clinicRequest);
    }

    @PostMapping("/{clinicId}/update")
    public ApiResponse<Clinic> updateClinics(@PathVariable Long clinicId, @RequestBody ClinicRequest clinicRequest) {
        return clinicService.updateClinicById(clinicId,clinicRequest);
    }
}
