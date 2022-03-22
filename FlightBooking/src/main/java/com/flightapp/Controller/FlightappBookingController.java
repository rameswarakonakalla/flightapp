package com.flightapp.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.model.UserRegister;
import com.flightapp.service.UserRegisterService;

@RestController
@RequestMapping("api/v1.0/flight")

public class FlightappBookingController {

	@Autowired
	UserRegisterService service;

	@PostMapping("/booking/{flightNumber}")
	public String bookFlightTicket(@RequestBody UserRegister register, @PathVariable Integer flightNumber) {

		return service.bookFlightTicket(register, flightNumber);

	}

	@GetMapping("/ticket/{pnr}")
	public Optional<UserRegister> getBookingDetails(@PathVariable String pnr) {

		return service.getBookingDetails(pnr);

	}

	@GetMapping("booking/history/{emailId}")
	public List<UserRegister> getBookingDetailsBasedOnEmail(@PathVariable String emailId) {
			
		return service.getBookingDetailsBasedOnEmail(emailId);
	}

	@DeleteMapping("booking/cancel/{pnr}")
	public String deleteBookingDetails(@PathVariable String pnr) {

		service.deleteBookingDetails(pnr);
		
		return pnr + " Details Deleted SucessFull ";

	}
	
	
	
	
}
