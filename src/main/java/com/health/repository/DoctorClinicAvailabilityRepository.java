package com.health.repository;

import com.health.entity.DoctorClinicAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface DoctorClinicAvailabilityRepository extends JpaRepository<DoctorClinicAvailability,Long> {
    List<DoctorClinicAvailability> findByDoctorClinicIdAndDayOfWeekAndIsActiveTrue(
            Long doctorClinicId, DayOfWeek dayOfWeek);
    List<DoctorClinicAvailability> findByDoctorClinicId(Long doctorClinicId);
}
