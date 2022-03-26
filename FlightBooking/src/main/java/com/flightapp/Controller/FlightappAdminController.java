package com.flightapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.exception.InvalidTokenException;
import com.flightapp.feignClients.AuthFeign;
import com.flightapp.model.Flightapp;
import com.flightapp.model.FlightappUpdate;
import com.flightapp.service.FlightappService;

@RestController
@RequestMapping("api/v1.0/flight")
public class FlightappAdminController {

	@Autowired
	FlightappService service;

	@Autowired
	private AuthFeign authFeign;

	@PostMapping("/airline/inventory/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Object> saveFlightInfo(@RequestHeader("Authorization") String token,
			@RequestBody Flightapp flightapp) {

		if (authFeign.getAdminValidity(token).getBody().isValid()) {
			return service.saveFlightInfo(flightapp);
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	}

	@PostMapping("/search")
	public ResponseEntity<Object> searchFlight(@RequestBody Flightapp flightapp) {
		ResponseEntity<Object> searchFlight = service.searchFlight(flightapp);
		return searchFlight;
	}
	
	@PatchMapping("/airline/inventory/add")
	public ResponseEntity<Object> saveInventory(@RequestHeader("Authorization") String token, @RequestBody FlightappUpdate flightapp) {

		if (authFeign.getAdminValidity(token).getBody().isValid()) {
			return service.saveInventory(flightapp);
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");

	}

}
