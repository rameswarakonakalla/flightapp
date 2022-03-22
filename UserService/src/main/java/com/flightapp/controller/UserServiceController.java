package com.flightapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.exception.UserExistsException;
import com.flightapp.model.AuthResponse;
import com.flightapp.model.LoginDetails;
import com.flightapp.model.UserData;
import com.flightapp.service.UserServices;

@RestController
@RequestMapping("/api/v1.0/flight/airline")
public class UserServiceController {

	@Autowired
	private UserServices userService;

	@PostMapping(value = "/register")
	public ResponseEntity<Object> register(@Valid @RequestBody UserData user) throws UserExistsException {
		return userService.register(user);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDetails userlogincredentials) {

		return userService.login(userlogincredentials);
	}

	@GetMapping(value = "/validate")
	public ResponseEntity<AuthResponse> getValidity(@RequestHeader("Authorization") final String token) {
		System.out.println("in side method {}" + token);
		return userService.validate(token);
	}

//	@GetMapping(value = "/users/all")
//	public ResponseEntity<Object> getAllUsers(){
//		return userService.getAllUsers();
//	}

//	@GetMapping(value = "/users/search/{username}")
//	public ResponseEntity<Object> searchByUsername(@PathVariable String username){
//		return userService.searchByUsername(username);
//	}

//	@PutMapping(value="/{username}/forgot")
//	public ResponseEntity<Object> forgotPassword(@PathVariable String username, @Valid @RequestBody LoginDetails data){
//		return userService.forgotPassword(data);
//	}

}
