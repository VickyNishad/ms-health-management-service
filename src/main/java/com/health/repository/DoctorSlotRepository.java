/**
 * 
 */
package com.health.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.DoctorSlot;

/**
 * 
 */
@Repository
public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, Long> {

    boolean existsByDoctorIdAndClinicIdAndSlotDateAndSlotStartTimeAndSlotEndTime(
            Long doctorId, Long clinicId, LocalDate slotDate,
            LocalTime slotStartTime, LocalTime slotEndTime);

    boolean existsByDoctorIdAndClinicIdAndSlotDate(
            Long doctorId, Long clinicId, LocalDate slotDate);

    List<DoctorSlot> findByDoctorIdAndClinicIdAndSlotDateGreaterThanEqualAndIsBookedFalse(
            Long doctorId, Long clinicId, LocalDate currentDate);

    List<DoctorSlot> findByDoctorIdAndClinicIdAndSlotDate(
            Long doctorId, Long clinicId, LocalDate slotDate);

    List<DoctorSlot> findByDoctorIdAndClinicIdAndSlotDateAndIsBookedFalse(
            Long doctorId, Long clinicId, LocalDate slotDate);

    List<DoctorSlot> findByDoctorIdAndClinicIdAndSlotDateAndStatus(
            Long doctorId, Long clinicId, LocalDate slotDate, String status);

    List<DoctorSlot> findByDoctorIdAndClinicIdAndSlotDateAndSlotStartTimeLessThanAndSlotEndTimeGreaterThan(
            Long doctorId, Long clinicId, LocalDate slotDate,
            LocalTime newSlotEndTime, LocalTime newSlotStartTime);

    List<DoctorSlot> findByDoctorIdAndClinicIdAndSlotDateBetween(
            Long doctorId, Long clinicId, LocalDate startDate, LocalDate endDate);

}