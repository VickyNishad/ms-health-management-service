package com.health.repository;

import java.util.List;

import com.health.model.QualificationMaster;



public interface QualificationMasterRepository {

	public List<QualificationMaster> findAllById(List<Long> id);
}
