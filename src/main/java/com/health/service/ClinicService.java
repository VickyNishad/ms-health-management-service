package com.health.service;

import com.health.dto.MessageResponse;
import com.health.dto.request.ClinicRequest;
import com.health.dto.response.ClinicDetailsDto;
import com.health.entity.Clinic;
import com.health.models.ApiResponse;

import java.util.List;

public interface ClinicService {
    ApiResponse<Clinic> createClinic(ClinicRequest clinic);
    ApiResponse<Clinic> updateClinicById(Long clinicId, ClinicRequest clinic);
    ApiResponse<MessageResponse> deleteClinicById(Long clinicId);
    ApiResponse<List<ClinicDetailsDto>> findClinicByDoctorId(Long userId);
    ApiResponse<Clinic> findById(Long clinicId);

}
