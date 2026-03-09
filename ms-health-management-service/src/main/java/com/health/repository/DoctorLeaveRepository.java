/**
 * 
 */
package com.health.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import com.health.domain.model.DoctorLeaveModel;
import com.health.entity.DoctorLeave;

/**
 * 
 */
public interface DoctorLeaveRepository {

	public DoctorLeaveModel save(DoctorLeaveModel model);

	public Optional<DoctorLeave> findById(Long id);

	public List<DoctorLeave> findOverlappingLeaves(Long doctorId, LocalDate startDate, LocalDate endDate);

	public List<DoctorLeave> getDoctorLeaves(Long doctorId);

	List<DoctorLeave> findByDoctorAndDate(Long doctorId, LocalDate date);
}
