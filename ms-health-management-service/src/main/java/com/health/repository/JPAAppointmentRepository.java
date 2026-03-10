/**
 * 
 */
package com.health.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.health.entity.Appointment;
import com.health.entity.Doctor;
import com.health.entity.DoctorSlot;
import com.health.entity.Patient;

import jakarta.persistence.LockModeType;

/**
 * 
 */
@Repository
public interface JPAAppointmentRepository extends JpaRepository<Appointment, Long> {

}
