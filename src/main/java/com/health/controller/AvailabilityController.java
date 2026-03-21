/**
 * 
 */
package com.health.controller;

import com.health.dto.response.DoctorClinicAvailabilityDto;
import com.health.models.ApiResponse;
import com.health.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 */
@RestController
@RequestMapping("/api/v1/availability")
public class AvailabilityController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/{userId}")
    public ApiResponse<List<DoctorClinicAvailabilityDto>> getAvailabilityClinicId(@PathVariable Long userId) {
        return doctorService.getAvailability(userId);
    }

}
