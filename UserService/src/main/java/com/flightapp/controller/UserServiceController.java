package com.flightapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.exception.UserExistsException;
import com.flightapp.model.AdminLoginDetails;
import com.flightapp.model.AuthResponse;
import com.flightapp.model.UserData;
import com.flightapp.service.AdminServices;
import com.flightapp.service.UserServices;

@RestController
@RequestMapping("/api/v1.0/flight/airline/")
public class UserServiceController {

	@Autowired
	private UserServices userService;
	
	@Autowired
	private AdminServices adminServices;

	@PostMapping(value = "/user/register")
	public ResponseEntity<Object> userRegister(@Valid @RequestBody UserData user) throws UserExistsException {
		return userService.userRegister(user);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<AuthResponse> userLogin( @Valid @RequestBody UserData userlogincredentials) {

		return userService.userLogin(userlogincredentials);
	}
	
	@PostMapping(value = "/adminlogin")
	public ResponseEntity<AuthResponse> adminLogin(@Valid @RequestBody AdminLoginDetails adminlogincredentials) {

		return adminServices.adminLogin(adminlogincredentials);
	}

	
	@GetMapping(value = "/validate")
	public ResponseEntity<AuthResponse> getValidity(@RequestHeader("Authorization") final String token) {
		System.out.println("in side method {}" + token);
		return userService.validate(token);
	}


	@GetMapping(value = "/adminvalidate")
	public ResponseEntity<AuthResponse> getAdminValidity(@RequestHeader("Authorization") final String token) {
		System.out.println("in side method {}" + token);
		return adminServices.adminValidate(token);
	}

}
