/**
 * 
 */
package com.health.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.health.domain.model.DoctorAvailabilityModel;
import com.health.entity.DoctorAvailability;
import com.health.repository.DoctorAvailabilityRepository;
import com.health.repository.jpa.JPADoctorAvailabilityRepository;

/**
 * 
 */
@Component
public class DoctorAvailabilityRepositoryImpl implements DoctorAvailabilityRepository {

	@Autowired
	public JPADoctorAvailabilityRepository jpaDoctorAvailabilityRepository;

	@Override
	public DoctorAvailability save(DoctorAvailabilityModel model) {
		// TODO Auto-generated method stub
		DoctorAvailability doctorAvailability = new DoctorAvailability();
		
		doctorAvailability.setAvailabilityId(model.getAvailabilityId());
		doctorAvailability.setClinic(model.getClinic());
		doctorAvailability.setDoctor(model.getDoctor());
		doctorAvailability.setDayOfWeek(model.getDayOfWeek());
		doctorAvailability.setStartTime(model.getStartTime());
		doctorAvailability.setEndTime(model.getEndTime());
		doctorAvailability.setSlotDuration(model.getSlotDuration());
		doctorAvailability.setCreatedAt(model.getCreatedAt());
		doctorAvailability.setCreatedBy(model.getCreatedBy());
		doctorAvailability.setUpdatedAt(model.getUpdatedAt());
		doctorAvailability.setUpdatedBy(model.getUpdatedBy());
		doctorAvailability.setIsActive(model.getIsActive());
		
		jpaDoctorAvailabilityRepository.save(doctorAvailability);
		return doctorAvailability;
	}

	@Override
	public DoctorAvailability update(DoctorAvailabilityModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<DoctorAvailability> getDoctorAvailability(Long doctorId, Long clinicId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<DoctorAvailability> findByDoctor_DoctorIdAndClinic_ClinicIdAndDayOfWeek(Long doctorId, Long clinicId,
			String dayOfWeek) {
		// TODO Auto-generated method stub
		return jpaDoctorAvailabilityRepository.findByDoctor_DoctorIdAndClinic_ClinicIdOrderByDayOfWeekAscStartTimeAsc(doctorId, clinicId);
	}

	@Override
	public List<DoctorAvailability> findByDoctor_DoctorIdAndDayOfWeek(Long doctorId, String dayOfWeek) {
		// TODO Auto-generated method stub
		return jpaDoctorAvailabilityRepository.findByDoctor_DoctorIdAndDayOfWeek(doctorId, dayOfWeek);
	}

	@Override
	public void deleteAll(List<DoctorAvailability> oldList) {
		// TODO Auto-generated method stub
		jpaDoctorAvailabilityRepository.deleteAll(oldList);
	}
	
	@Override
	public void deleteById(Long availability) {
		// TODO Auto-generated method stub
		jpaDoctorAvailabilityRepository.deleteById(availability);
	}

	@Override
	public List<DoctorAvailability> findByDoctor_DoctorIdOrderByDayOfWeekAscStartTimeAsc(Long doctorId) {
		// TODO Auto-generated method stub
		return jpaDoctorAvailabilityRepository.findByDoctor_DoctorIdOrderByDayOfWeekAscStartTimeAsc( doctorId);
	}

	@Override
	public Optional<DoctorAvailability> findById(Long availabilityId) {
		// TODO Auto-generated method stub
		return jpaDoctorAvailabilityRepository.findById(availabilityId);
	}

	@Override
	public List<DoctorAvailability> findByDoctorClinicAndDay(Long doctorId, Long clinicId, String dayOfWeek) {
		// TODO Auto-generated method stub
		return jpaDoctorAvailabilityRepository.findByDoctorClinicAndDay(doctorId, clinicId, dayOfWeek);
	}

}
