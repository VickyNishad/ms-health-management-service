package com.health.repository;

import com.health.entity.DoctorSearchIndex;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorSearchIndexRepository extends JpaRepository<DoctorSearchIndex, Long> {

//    @Query(value = "SELECT * FROM doctor_search_index d " +
//            "WHERE MATCH(name, clinic_name, specialization_names, clinic_address,city, locality,pin_code) " +
//            "AGAINST(:keyword IN NATURAL LANGUAGE MODE)",
//            countQuery = "SELECT count(*) FROM doctor_search_index",
//            nativeQuery = true)
//    Page<DoctorSearchIndex> searchFullText(
//            @Param("keyword") String keyword,
//            Pageable pageable
//    );

    @Query(value = "SELECT * FROM doctor_search_index d " +
            "WHERE MATCH(name, clinic_name, specialization_names, clinic_address, city, locality, pin_code) " +
            "AGAINST(CONCAT(:keyword, '*') IN BOOLEAN MODE) " +
            "ORDER BY d.total_experience DESC",
            countQuery = "SELECT count(*) FROM doctor_search_index",
            nativeQuery = true)
    Page<DoctorSearchIndex> searchFullText(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
