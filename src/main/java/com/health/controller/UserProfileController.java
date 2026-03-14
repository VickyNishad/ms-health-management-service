/**
 * 
 */
package com.health.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/user")
public class UserProfileController {
	
	@GetMapping
	public void user() {
		
	}
	
	@PostMapping
	public void users() {
		
	}
	
	@PostMapping("/update")
	public void userUpdate() {
		
	}
	
	@DeleteMapping
	public void delete() {
		
	}
}
