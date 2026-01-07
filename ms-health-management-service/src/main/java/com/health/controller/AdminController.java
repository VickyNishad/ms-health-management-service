/**
 * 
 */
package com.health.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
@RestController
@RequestMapping("/medicque/admin")
public class AdminController {

	@GetMapping("/get")
	public String get() {
		return  "get";
	}
}
