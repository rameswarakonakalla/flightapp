package com.flightapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.exception.AdminLoginFailedException;
import com.flightapp.exception.InvalidTokenException;
import com.flightapp.feignClients.AuthFeign;
import com.flightapp.model.AdminLoginDetails;
import com.flightapp.model.Flightapp;
import com.flightapp.service.FlightappService;

@RestController
@RequestMapping("api/v1.0/flight")
public class FlightappController {

	@Autowired
	FlightappService service;

	@Autowired
	private AuthFeign authFeign;

	@PostMapping("/admin/login")
	public String adminLogin(@RequestBody AdminLoginDetails details) {
		Boolean adminLogin = service.adminLogin(details);
		if (adminLogin) {
			return "LoginSucess";
		} else {
			throw new AdminLoginFailedException("Admin Access Denied ..!! ,, Try Again ...!!!");

		}

	}


	@PostMapping("/airline/register")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Object> saveFlightInfo(@RequestHeader("Authorization") String token,
			@RequestBody Flightapp flightapp) {

		// return service.saveFlightInfo(flightapp);

		if (authFeign.getValidity(token).getBody().isValid()) {
			return service.saveFlightInfo(flightapp);
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	}

//	@PostMapping("/airline/inventory/add")
//	public String saveInventory(@RequestHeader("Authorization") String token, @RequestBody Flightapp flightapp) {
//
//		if (authFeign.getValidity(token).getBody().isValid()) {
//			return service.saveInventory(flightapp);
//		}
//		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
//
//	}

	@PostMapping("/search")
	public ResponseEntity<Object> searchFlight(@RequestBody Flightapp flightapp) {
		ResponseEntity<Object> searchFlight = service.searchFlight(flightapp);
		return searchFlight;
	}

}
