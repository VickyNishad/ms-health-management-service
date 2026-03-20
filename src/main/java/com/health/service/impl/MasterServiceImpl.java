package com.health.service.impl;

import com.health.dto.response.MasterSummary;
import com.health.entity.QualificationMaster;
import com.health.entity.RoleMaster;
import com.health.entity.SpecializationMaster;
import com.health.models.ApiResponse;
import com.health.repository.QualificationMasterRepository;
import com.health.repository.RoleMasterRepository;
import com.health.repository.SpecializationMasterRepository;
import com.health.service.MasterService;
import com.health.utility.ApiExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MasterServiceImpl implements MasterService {

    @Autowired
    private RoleMasterRepository roleMasterRepository;

    @Autowired
    private QualificationMasterRepository qualificationMasterRepository;

    @Autowired
    private SpecializationMasterRepository specializationMasterRepository;
    /**
     * @return
     */
    @Override
    public ApiResponse<?> qualificationMaster() {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,req ->{},()->{
            List<MasterSummary> summaries = new ArrayList<>();
            List<QualificationMaster> qualificationMasters = qualificationMasterRepository.findAll();
            qualificationMasters.forEach(qm->{
                summaries.add(new  MasterSummary(qm.getId(),qm.getQualificationName(),qm.getDescription()));
            });
            return summaries;
        },ApiResponse::success);
    }

    /**
     * @return
     */
    @Override
    public ApiResponse<?> getQualificationById(Long id) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,req ->{},()->{
            Optional<QualificationMaster> optional = qualificationMasterRepository.findById(id);
            return new MasterSummary(optional.get().getId(),optional.get().getQualificationName(),optional.get().getDescription());
        },ApiResponse::success);
    }

    /**
     * @return
     */
    @Override
    public ApiResponse<List<MasterSummary>> getRoleMaster() {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,req ->{},()->{
            List<MasterSummary> summaries = new ArrayList<>();

            List<RoleMaster> roleMasters = roleMasterRepository.findAll();
            roleMasters.forEach(roleMaster->{
                summaries.add(new MasterSummary(roleMaster.getId(),roleMaster.getRoleName(),""));
            });
            return summaries;
        },ApiResponse::success);
    }

    /**
     * @return
     */
    @Override
    public ApiResponse<?> getRoleById(Long id) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,req ->{},()->{
            Optional<RoleMaster> roleMaster = roleMasterRepository.findById(id);
            return new MasterSummary(roleMaster.get().getId(),roleMaster.get().getRoleName(),"");
        },ApiResponse::success);
    }

    /**
     * @return
     */
    @Override
    public ApiResponse<?> specializationById(Long id) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,req ->{},()->{
            Optional<SpecializationMaster> specializationMaster = specializationMasterRepository.findById(id);
            return new MasterSummary(specializationMaster.get().getId(),specializationMaster.get().getSpecializationName(),specializationMaster.get().getDescription());
        },ApiResponse::success);
    }

    /**
     * @return
     */
    @Override
    public ApiResponse<?> getSpecializationMaster() {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,req ->{},()->{
            List<MasterSummary> summaries = new ArrayList<>();
            List<SpecializationMaster> specializationMasters = specializationMasterRepository.findAll();
            specializationMasters.forEach(specializationMaster->{
                summaries.add(new MasterSummary(specializationMaster.getId(),specializationMaster.getSpecializationName(),specializationMaster.getDescription()));
            });
            return summaries;
        },ApiResponse::success);
    }
}
