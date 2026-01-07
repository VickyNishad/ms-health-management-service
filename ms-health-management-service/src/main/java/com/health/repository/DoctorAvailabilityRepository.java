/**
 * 
 */
package com.health.repository;

import java.util.List;
import java.util.Optional;

import com.health.domain.model.DoctorAvailabilityModel;
import com.health.model.DoctorAvailability;

/**
 * 
 */
public interface DoctorAvailabilityRepository {

	public Optional<DoctorAvailability> findById(Long availabilityId);

	public DoctorAvailability save(DoctorAvailabilityModel model);

	public DoctorAvailability update(DoctorAvailabilityModel model);

	public Optional<DoctorAvailability> getDoctorAvailability(Long doctorId, Long clinicId);

	List<DoctorAvailability> findByDoctor_DoctorIdAndClinic_ClinicIdAndDayOfWeek(Long doctorId, Long clinicId,
			String dayOfWeek);

	public List<DoctorAvailability> findByDoctor_DoctorIdAndDayOfWeek(Long doctorId, String dayOfWeek);

	public void deleteAll(List<DoctorAvailability> oldList);

	public void deleteById(Long availability);

	List<DoctorAvailability> findByDoctor_DoctorIdOrderByDayOfWeekAscStartTimeAsc(Long doctorId);
	
	List<DoctorAvailability> findByDoctorClinicAndDay(Long doctorId, Long clinicId, String dayOfWeek);

}
