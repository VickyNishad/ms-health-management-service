/**
 * 
 */
package com.health.repository.jpa;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.health.entity.DoctorSlot;


/**
 * 
 */
@Repository
public interface JPADoctorSlotRepository extends JpaRepository<DoctorSlot, Long> {

	@Query("SELECT s FROM DoctorSlot s WHERE s.doctorId = :doctorId AND s.clinicId = :clinicId AND s.slotDate >= :startDate")
	List<DoctorSlot> findFutureSlots(Long doctorId, Long clinicId, LocalDate startDate);

	@Modifying
	@Query("DELETE FROM DoctorSlot s WHERE s.doctorId = :doctorId AND s.clinicId = :clinicId AND s.slotDate >= :startDate AND s.isBooked = false")
	void deleteOnlyFreeFutureSlots(Long doctorId, Long clinicId, LocalDate startDate);

	@Query("SELECT MAX(s.token) FROM DoctorSlot s WHERE s.doctorId = :doctorId AND s.clinicId = :clinicId AND s.slotDate = :date")
	Integer getMaxTokenForDate(Long doctorId, Long clinicId, LocalDate date);

	@Query("SELECT s FROM DoctorSlot s WHERE s.doctorId = :doctorId AND s.clinicId = :clinicId")
	List<DoctorSlot> findByDoctorAndClinic(@Param("doctorId") Long doctorId, @Param("clinicId") Long clinicId);
	
	
	 @Query("SELECT s FROM DoctorSlot s " +
	           "WHERE s.doctorId = :doctorId AND s.clinicId = :clinicId AND s.slotDate = :date " +
	           "ORDER BY s.slotTime ASC")
	    List<DoctorSlot> findSlotsByDoctorClinicAndDate(@Param("doctorId") Long doctorId,
	                                                    @Param("clinicId") Long clinicId,
	                                                    @Param("date") LocalDate date);
	 
	 
	 List<DoctorSlot> findByDoctorIdAndClinicIdAndSlotDate(
	            Long doctorId,
	            Long clinicId,
	            LocalDate slotDate
	    );
	 

}