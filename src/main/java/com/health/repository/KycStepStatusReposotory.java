/**
 * 
 */
package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.KycStepStatus;

/**
 * 
 */
@Repository
public interface KycStepStatusReposotory extends JpaRepository<KycStepStatus, Long> {

	List<KycStepStatus> findByUser_Id(Long userId);
}
