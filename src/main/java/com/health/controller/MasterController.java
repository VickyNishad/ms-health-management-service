/**
 * 
 */
package com.health.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.entity.RoleMaster;
import com.health.models.ApiResponse;
import com.health.repository.RoleMasterRepository;
import com.health.service.RoleMasterService;

/**
 * 
 */
@RestController
@RequestMapping("/api/v1/master")
public class MasterController {
	
	@Autowired
	private RoleMasterService roleMasterService;
	
	@Autowired
	private RoleMasterRepository roleMasterRepository;

	@GetMapping("/role")
	public ResponseEntity<ApiResponse<List<RoleMaster>>> roles(){
		return roleMasterService.findAll();
	}
	
	@GetMapping("/role/{userId}")
	public ResponseEntity<ApiResponse<List<RoleMaster>>> role(@PathVariable(name = "userId")Long userId){
		return roleMasterService.findByUserId(userId);
	}
}
