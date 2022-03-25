package com.flightapp.seviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.flightapp.model.AdminLoginDetails;
import com.flightapp.model.AuthResponse;
import com.flightapp.repository.AdminRepo;
import com.flightapp.service.AdminServices;

public class AdminServiceImpl implements AdminServices {

	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private AdminDetailsService adminDetailsService;
	
	@Autowired
	private JwtUtil jwtutil;
	
	@Override
	public ResponseEntity<AuthResponse> adminValidate(String authToken) {
		String token1 = authToken.substring(7);
		AuthResponse res = new AuthResponse();
		if (Boolean.TRUE.equals(jwtutil.validateToken(token1))) {
			res.setUsername(jwtutil.extractUsername(token1));
			res.setValid(true);
			Optional<AdminLoginDetails> adminUser = adminRepo.findById(jwtutil.extractUsername(token1));
			if(adminUser.isPresent()) {
				res.setUsername(adminUser.get().getUsername());
				res.setValid(true);
				res.setToken("token successfully validated");
				
			}
		} else {
			res.setValid(false);
			res.setToken("Invalid Token Received");
			
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<AuthResponse> adminLogin(AdminLoginDetails adminLoginDetails) {
		
		final UserDetails userdetails = adminDetailsService.loadUserByUsername(adminLoginDetails.getUsername());
		String uid = "";
		String generateToken = "";
		if (userdetails.getPassword().equals(adminLoginDetails.getPassword())) {
			uid = adminLoginDetails.getUsername();
			generateToken = jwtutil.generateToken(userdetails);
			
			return new ResponseEntity<>(new AuthResponse(uid,true,generateToken), HttpStatus.OK);
		} else {
			
			
			return new ResponseEntity<>(new AuthResponse(adminLoginDetails.getUsername(),false,"In Correct Password"), HttpStatus.FORBIDDEN);
		}
	}
}
