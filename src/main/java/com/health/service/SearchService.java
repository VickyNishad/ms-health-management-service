package com.health.service;

import com.health.dto.response.DoctorSearchSummary;
import com.health.models.ApiResponse;
import org.springframework.data.domain.Page;

public interface SearchService {
    ApiResponse<Page<DoctorSearchSummary>> searchFullText(String keyword, int page, int size);
}
