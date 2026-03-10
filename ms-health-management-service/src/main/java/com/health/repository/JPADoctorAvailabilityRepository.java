package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.health.entity.DoctorAvailability;

@Repository
public interface JPADoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {


}
