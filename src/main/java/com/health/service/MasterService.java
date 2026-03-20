package com.health.service;

import com.health.dto.response.MasterSummary;
import com.health.models.ApiResponse;

import java.util.List;

public interface MasterService {

    public ApiResponse<?> qualificationMaster();
    public ApiResponse<?> getQualificationById(Long id);

    public ApiResponse<List<MasterSummary>> getRoleMaster();
    public ApiResponse<?> getRoleById(Long id);

    public ApiResponse<?> specializationById(Long id);
    public ApiResponse<?> getSpecializationMaster();

}
