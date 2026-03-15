package com.health.service.impl;

import com.health.dto.request.ClinicRequest;
import com.health.entity.Clinic;
import com.health.models.ApiResponse;
import com.health.repository.ClinicRepository;
import com.health.repository.DoctorClinicRepository;
import com.health.service.ClinicService;
import com.health.utility.ApiExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private DoctorClinicRepository doctorClinicRepository;
    /**
     * @param userId
     * @param clinic
     * @return
     */
    @Override
    public ApiResponse<?> createClinic(Long userId, ClinicRequest clinic) {

        ApiResponse<?> success = ApiExecutionUtils.ApiExecutor.processRequest(null,req ->{},() -> {
            return null;
        },ApiResponse::success);

        return null;
    }
}
