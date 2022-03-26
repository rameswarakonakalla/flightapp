package com.flightapp.seviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.flightapp.exception.UserExistsException;
import com.flightapp.model.AuthResponse;
import com.flightapp.model.UserData;
import com.flightapp.repository.UserRepository;
import com.flightapp.service.UserServices;

@Service
public class UserServiceImpl implements UserServices {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomerDetailsService custdetailservice;

	@Autowired
	private JwtUtil jwtutil;

	public ResponseEntity<Object> userRegister(UserData userData) {
		Optional<UserData> user = userRepository.findById(userData.getEmailid());
		if (user.isEmpty()) {
			try {
				userRepository.save(userData);
				return new ResponseEntity<>("User Added Successfully", HttpStatus.CREATED);
			} catch (Exception ex) {
				throw new UserExistsException("Login id already Exists");
			}

		} else {

			throw new UserExistsException("Username already Exists");
		}
	}

	@Override
	public ResponseEntity<AuthResponse> userLogin(UserData loginDetails) {
		final UserDetails userdetails = custdetailservice.loadUserByUsername(loginDetails.getEmailid());
		String uid = "";
		String generateToken = "";
		if (userdetails.getPassword().equals(loginDetails.getPassword())) {
			uid = loginDetails.getEmailid();
			generateToken = jwtutil.generateToken(userdetails);

			return new ResponseEntity<>(new AuthResponse(uid, true, generateToken), HttpStatus.OK);
		} else {

			return new ResponseEntity<>(new AuthResponse(loginDetails.getEmailid(), false, "In Correct Password"),
					HttpStatus.FORBIDDEN);
		}
	}

	@Override
	public ResponseEntity<AuthResponse> validate(String authToken) {
		String token1 = authToken.substring(7);
		AuthResponse res = new AuthResponse();
		if (Boolean.TRUE.equals(jwtutil.validateToken(token1))) {
			res.setUsername(jwtutil.extractUsername(token1));
			res.setValid(true);
			Optional<UserData> user1 = userRepository.findById(jwtutil.extractUsername(token1));
			if (user1.isPresent()) {
				res.setUsername(user1.get().getEmailid());
				res.setValid(true);
				res.setToken("token successfully validated");

			}
		} else {
			res.setValid(false);
			res.setToken("Invalid Token Received");

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
