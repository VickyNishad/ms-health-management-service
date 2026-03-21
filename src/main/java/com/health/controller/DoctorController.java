/**
 * 
 */
package com.health.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.service.DoctorService;

/**
 * 
 */
@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

}
