package com.health.service.impl;

import com.health.dto.response.DoctorSearchSummary;
import com.health.entity.DoctorSearchIndex;
import com.health.models.ApiResponse;
import com.health.repository.DoctorSearchIndexRepository;
import com.health.service.SearchService;
import com.health.utility.ApiExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private DoctorSearchIndexRepository doctorSearchIndexRepository;

    @Override
    public ApiResponse<Page<DoctorSearchSummary>> searchFullText(String keyword, int page, int size) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,req ->{

            if (keyword == null || keyword.trim().isEmpty()) {
                throw new RuntimeException("Keyword must not be empty");
            }
        },()->{
            Pageable pageable = PageRequest.of(
                    page,
                    size,
                    Sort.by(Sort.Direction.DESC,"total_experience")
            );
            Page<DoctorSearchIndex> pageResult = doctorSearchIndexRepository.searchFullText(keyword,pageable);

            return pageResult.map(d -> new DoctorSearchSummary(
                    d.getName(),
                    d.getDoctorId(),
                    d.getProfilePicture(),
                    d.getClinicName(),
                    d.getClinicId(),
                    d.getSpecializationNames(),
                    d.getQualifications(),
                    d.getTotalExperience(),
                    d.getClinicAddress(),
                    d.getAvailable()
            ));
        },ApiResponse::success);
    }
}
