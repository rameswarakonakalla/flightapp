package com.flightapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.model.AdminLoginDetails;
import com.flightapp.model.AuthResponse;

@Service
public interface AdminServices {

	public ResponseEntity<AuthResponse> adminLogin(AdminLoginDetails adminLoginDetails);
	
	public ResponseEntity<AuthResponse> adminValidate(String authToken);
}
