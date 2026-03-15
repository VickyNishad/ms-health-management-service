package com.health.service;

import com.health.dto.request.ClinicRequest;
import com.health.entity.Clinic;
import com.health.models.ApiResponse;

import java.util.List;

public interface ClinicService {
    ApiResponse<?> createClinic(Long userId ,ClinicRequest clinic);
}
