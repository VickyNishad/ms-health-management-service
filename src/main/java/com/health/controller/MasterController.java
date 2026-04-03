/**
 * 
 */
package com.health.controller;

import java.util.List;

import com.health.dto.response.MasterSummary;
import com.health.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.dto.response.ApiResponse;

/**
 * 
 */
@RestController
@RequestMapping("/api/v1/master")
public class MasterController {

	@Autowired
	private MasterService masterService;

	@GetMapping("/qualifications")
	public ApiResponse<?> qualificationMaster(){
		return masterService.qualificationMaster();
	}
	@GetMapping("/{id}/qualification")
	public ApiResponse<?> getQualificationById(@PathVariable Long id){
		return masterService.getQualificationById(id);
	}
	@GetMapping("/roles")
	public ApiResponse<List<MasterSummary>> getRoleMaster(){
		return masterService.getRoleMaster();
	}
	@GetMapping("/{id}/role")
	public ApiResponse<?> getRoleById(@PathVariable Long id){
		return masterService.getRoleById(id);
	}
	@GetMapping("/{id}/specialization")
	public ApiResponse<?> specializationById(@PathVariable Long id){
		return masterService.specializationById(id);
	}
	@GetMapping("/specializations")
	public ApiResponse<?> getSpecializationMaster(){
		return masterService.getSpecializationMaster();
	}
}
